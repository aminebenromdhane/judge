package com.orion.judge.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.orion.judge.scripts.SFDownloadDocs;
import com.orion.judge.scripts.SFOcrDocs;


@RestController
public class VerificatorController {
	
	@RequestMapping(value = "verificator/countpdf",method = RequestMethod.GET)
	public String countpdf() {
		return String.valueOf(SFDownloadDocs.goods);
	}
	
	@RequestMapping(value = "verificator/countfail",method = RequestMethod.GET)
	public String countfail() {
		return String.valueOf(SFDownloadDocs.fails);
	}
	
	@RequestMapping(value = "verificator/countexist",method = RequestMethod.GET)
	public String countexist() {
		return String.valueOf(SFDownloadDocs.exists);
	}
	
	@RequestMapping(value = "verificator/countwaiting",method = RequestMethod.GET)
	public String countwaiting() {
		return String.valueOf(SFDownloadDocs.waitings);
	}
	
	@RequestMapping(value = "verificator/goodocr",method = RequestMethod.GET)
	public String goodocr() {
		return String.valueOf(SFOcrDocs.goods);
	}
	
	@RequestMapping(value = "verificator/failocr",method = RequestMethod.GET)
	public String failocr() {
		return String.valueOf(SFOcrDocs.fails);
	}
	
	@RequestMapping(value = "verificator/existocr",method = RequestMethod.GET)
	public String existocr() {
		return String.valueOf(SFOcrDocs.exists);
	}
}


