package com.shinhan.soloplay.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.shinhan.soloplay.card.CardRepository;

public class UserServcieImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CardRepository cardRepository;
}
