package com.redskt.classroom.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tanyuehong
 * @since 2021-04-01
 */
@RestController
@RequestMapping("/classroom/edu-book-chapters")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class  EduBookChaptersController {

}

