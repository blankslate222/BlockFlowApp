package edu.sjsu.team113.service;

import edu.sjsu.team113.model.WorkGroup;

public interface IManagerUserService {
	WorkGroup createGroup(WorkGroup group);

	WorkGroup updateGroup(WorkGroup group);

	boolean deleteGroup(WorkGroup group);
}
