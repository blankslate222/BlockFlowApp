package edu.sjsu.team113.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.NodeStatus;
import edu.sjsu.team113.model.Request;
import edu.sjsu.team113.model.RequestNode;
import edu.sjsu.team113.model.RequestStatus;
import edu.sjsu.team113.repository.RequestNodeRepository;
import edu.sjsu.team113.repository.RequestRepository;
import edu.sjsu.team113.repository.WorkflowRepository;

public class StaffUserService implements IStaffUserService {

	@Autowired
	private RequestNodeRepository nodeRepo;

	@Autowired
	private RequestRepository reqRepo;

	@Autowired
	private IDataService dataService;

	@Autowired
	private WorkflowRepository flowRepo;

	@Autowired
	private IBlockchainService chainService;

	@Value("${chain.server}")
	private String openchainServer;

	@Override
	public String changeRequestNodeStatus(Long requestNodeId, Long requestId,
			String status) {
		String transactionHex = null;
		Request request = reqRepo.findOne(requestId);
		ClientOrg flowClient = request.getWorkflow().getClient();

		NodeStatus newStatus = NodeStatus.valueOf(status);
		List<RequestNode> nodes = nodeRepo
				.findByRequestOrderByLevelAsc(request);

		int lastLevel = nodes.get(nodes.size() - 1).getLevel();

		int n = nodes.size();
		NodeStatus nextnodestatus = null;
		RequestStatus nextstatus = null;
		for (int i = 0; i < n; i++) {
			RequestNode node = nodes.get(i);
			if (node.getId() == requestNodeId) {
				switch (status) {
				case "APPROVED":
					nextnodestatus = NodeStatus.APPROVED;
					// current no longer current node
					node.setCurrentNode(false);
		
					if (node.getLevel() == lastLevel) {
						nextstatus = RequestStatus.APPROVED;
						changeRequestStatus(request, nextstatus, flowClient);
						changeNodeStatus(node, nextnodestatus, flowClient);
						break;
					}
					changeNodeStatus(node, nextnodestatus, flowClient);
					// TODO: change node active value of next node
					RequestNode nextnode = nodes.get(i + 1);
					nextnode.setCurrentNode(true);
					nextnode.setStatus(NodeStatus.PENDING_ACTION);
					nextnode.setModified(new Timestamp(new Date().getTime()));
					nodeRepo.save(nextnode);
					break;
				case "REJECTED":
					nextnodestatus = NodeStatus.REJECTED;
					nextstatus = RequestStatus.REJECTED;
					changeNodeStatus(node, nextnodestatus, flowClient);
					// change node status and current value of remaining nodes
					for (int j = i; j < n; j++) {
						RequestNode unreachedNode = nodes.get(j);
						unreachedNode.setStatus(NodeStatus.REJECTED);
						nodeRepo.save(unreachedNode);
					}
					changeRequestStatus(request, nextstatus, flowClient);
					break;
				default:
					System.out.println("not a legal state");
					break;
				}
				break;
			}
		}
		return transactionHex;
	}

	private void changeNodeStatus(RequestNode node, NodeStatus newstatus,
			ClientOrg client) {
		// TODO Auto-generated method stub
		String seed = null;
		ObjectMapper mapper = new ObjectMapper();
		node.setStatus(newstatus);
		Timestamp modified = new Timestamp(new Date().getTime());
		node.setModified(modified);
		try {
			String jsonString = mapper.writeValueAsString(node);
			seed = dataService.getSeedForClient(client);
			JSONObject obj = new JSONObject();
			obj.put("data", jsonString);
			obj.put("host", openchainServer);
			obj.put("seed", seed);
			String mutationHash = chainService.createTransaction(obj
					.toJSONString());
			// insert into database
			node.setMutationHash(mutationHash);
			nodeRepo.save(node);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void changeRequestStatus(Request request, RequestStatus newstatus,
			ClientOrg client) {
		// TODO Auto-generated method stub
		String seed = null;
		ObjectMapper mapper = new ObjectMapper();
		request.setStatus(newstatus);
		Timestamp modified = new Timestamp(new Date().getTime());
		request.setModified(modified);
		try {
			String jsonString = mapper.writeValueAsString(request);
			seed = dataService.getSeedForClient(client);
			JSONObject obj = new JSONObject();
			obj.put("data", jsonString);
			obj.put("host", openchainServer);
			obj.put("seed", seed);
			String mutationHash = chainService.createTransaction(obj
					.toJSONString());
			// insert into database
			request.setMutationHash(mutationHash);
			reqRepo.save(request);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}