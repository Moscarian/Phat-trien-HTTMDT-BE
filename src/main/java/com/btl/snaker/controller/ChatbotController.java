package com.btl.snaker.controller;

import com.btl.snaker.entity.Question;
import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestParam String message) {
        ResponseData responseData = new ResponseData();

        // Kiểm tra nếu tin nhắn rỗng
        if (message == null || message.trim().isEmpty()) {
            responseData.setSuccess(false);
            responseData.setDescription("Message cannot be empty");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // Xử lý tin nhắn và lấy dữ liệu từ cơ sở dữ liệu
        String botResponse = processMessage(message);

        responseData.setSuccess(true);
        responseData.setData(botResponse);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    private String processMessage(String message) {
        message = message.toLowerCase().trim();

        // Tìm câu hỏi trong cơ sở dữ liệu dựa trên tin nhắn của người dùng
        List<Question> matchingQuestions = questionRepository.findByQuestionContainingIgnoreCase(message);

        if (matchingQuestions.isEmpty()) {
            return "Xin lỗi, tôi không thể trả lời cho câu hỏi này của bạn. Bạn có thể thử hỏi lại bằng cách khác không?";
        }

        // Lấy câu trả lời từ câu hỏi đầu tiên khớp
        return matchingQuestions.get(0).getAnswer();
    }
}