package com.lb.client.ui.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

class MyRunnable implements Runnable{
	int sleep = 0;
	
	public MyRunnable(int sleep) {
		this.sleep = sleep * 1000;
	}
	
	@Override
	public void run() {
		System.out.println("Inside run block in MyRunnable");
		try {
			System.out.println("sleep: " + sleep);
			Thread.sleep(sleep);
		}catch(InterruptedException ie){
			System.out.println("InterruptedException in Thread");
		}catch(Exception e) {
			System.out.println("Excetpion in Thread");
		}
		finally {
			System.out.println("Thread complete");
		}
	}
}

@RestController
@RequestMapping("/api")
public class UserController{
	int sleep;
	
	@GetMapping("/getRequest")
	public ResponseEntity getRequest(@RequestParam(value = "sleep", defaultValue = "20") int sleep) {
		this.sleep = sleep;
		String response =  "Request Executed by GET Method";
		
		MyRunnable mr = new MyRunnable(sleep);
		mr.run();
		
		System.out.println(response);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@PostMapping("/createRequest")
	public ResponseEntity createRequest(@RequestParam(value = "sleep", defaultValue = "3", required = false) int sleep) {
		this.sleep = sleep;
		
		MyRunnable mr = new MyRunnable(sleep);
		mr.run();
		
		String response =  "Request Executed by POST Method";
		System.out.println(response);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}