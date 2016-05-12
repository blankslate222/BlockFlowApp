package edu.sjsu.team113;

import java.util.Map;
import java.util.Scanner;

public class t1 {
	private static int getCount(int[] nos) {
		// TODO Auto-generated method stub
		int res=0;
		int local=0;
		int i=1;
		int prev;
		while(i<nos.length){
			prev=nos[i-1];
			local=0;
			if(prev==0 || prev==-nos[i]){
				local=1;
				int c=0;
				while(i<nos.length && (prev==-nos[i] || prev==0)){
					c++;
					i=prev==0?i++:i+2;
					prev=nos[i-1];
				}
				local=c>1?(int)Math.pow(c, 2)-1:1;
			}else
				i++;
			
			res+=local;
		}
		if(i-1<nos.length && nos[i-1]==0)
			res++;
		
		return res;
	}

	public static void main(String args[]){
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();
		int[] nos=new int[n];
		for(int i=0;i<n;i++){
			nos[i]= reader.nextInt();
		}
		System.out.println(getCount(nos));
	}
}
