package com.example.urlshortener;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class homeController {
	private final BasicService bs;
	private final HashService hs;
	private final Base62Service b62s;
	public homeController(BasicService bs,HashService hs,Base62Service b62s) {
		this.bs=bs;
		this.hs = hs;
		this.b62s=b62s;
	}
	public static void main(String[] args) {
	}
	@GetMapping("/")
	public String login() {
		return "home";
	}
	@PostMapping("/shorten")
	public String handleShorten(@RequestParam String longUrl,Model model) {
		//String code = bs.shortenUrl(longUrl);
		//String code = hs.shortenUrl(longUrl);
		String code = b62s.encode(longUrl);
		model.addAttribute("shortened", "localhost:8080/"+code);
		return "shorten";
	}
	@GetMapping("/{code}")
	public String redirect(@PathVariable String code) {
		//String longUrl = bs.expandUrl(code);
		//String longUrl = hs.expandUrl(code);
		String longUrl = b62s.decode(code);
		if(longUrl!=null) return "redirect:"+longUrl;
		else return "error";
		
	}

}
