package com.example.urlshortener;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class homeController {
	private final basicService bs;
	public homeController(basicService bs) {
		this.bs=bs;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	@GetMapping("/")
	public String login() {
		return "home";
	}
	@PostMapping("/shorten")
	public String handleShorten(@RequestParam String longUrl,Model model) {
		String code = bs.shortenUrl(longUrl);
		model.addAttribute("shortened", "localhost:8080/"+code);
		return "shorten";
	}

}
