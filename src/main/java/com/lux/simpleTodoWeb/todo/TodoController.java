package com.lux.simpleTodoWeb.todo;

import com.lux.simpleTodoWeb.security.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TodoController {

    @Autowired
    public TodoController(UserService us,TodoRepository todoRepository) {
        this.us = us;
        this.todoRepository = todoRepository;
    }

    private UserService us;
    private TodoRepository todoRepository;

    public TodoController(){}

    @RequestMapping("todos")
    public ModelAndView showTodos(){
        List<Todo> alltodos = todoRepository.findAll();
        ModelAndView mv = new ModelAndView("todos");
        mv.addObject("alltodos",alltodos);
        mv.addObject("name", us.getLoggedUsername());
        return mv;
    }

    @RequestMapping(value = "create/todo",method = RequestMethod.GET)
    public  ModelAndView newTodo(){
        ModelAndView mv = new ModelAndView("newTodo");
        mv.addObject(new Todo());
        return mv;
    }

    @RequestMapping(value = "create/todo",method = RequestMethod.POST)
    public ModelAndView addTodos(
           @Valid Todo todo,
           BindingResult br
    ){
        ModelAndView mv = new ModelAndView("newTodo");
        if(br.hasErrors()){
            return mv;
        }
        mv.setViewName("redirect:/todos");
        todo.setDone(false);
        todoRepository.save(todo);
        return mv;
    }


    @RequestMapping("todo/delete/{id}")
    public ModelAndView deleteTodo(
            @PathVariable String id
    ){
        ModelAndView mv = new ModelAndView("redirect:/todos");
        todoRepository.deleteById(Integer.parseInt(id));
        return mv;
    }
    @RequestMapping("/todo/done/{id}")
    public ModelAndView setDoneAsTrue(@PathVariable String id){
        ModelAndView mv = new ModelAndView("redirect:/todos");
        Todo todo = todoRepository.findById(Integer.parseInt(id)).orElseThrow();
        todo.setDone(true);
        todoRepository.save(todo);
        return mv;
    }

}
