package com.example.PrimeHost.Controllers;

import com.example.PrimeHost.models.Poost;
import com.example.PrimeHost.repozitory.PoostRepozitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    //создаем переменную которая ссылается на наш репозиторий-интерфейс
    @Autowired
    private PoostRepozitory poostRepozitory;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        model.addAttribute("title", "Блог");
        //массив в которм будут содержать значения из таблички бд
        Iterable<Poost> poosts = poostRepozitory.findAll();
        model.addAttribute("poosts", poosts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        model.addAttribute("title", "Блог");
        return "blog-Add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons,@RequestParam String full_text, Model model) {
        Poost poost = new Poost(title, anons, full_text);
        model.addAttribute("title", "Блог");
        //сохранить объект на основе класса пост
        poostRepozitory.save(poost);
        return "redirect:/blog";
    }

//@PathVariable() для принятия динамического параметра
    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        // чтобы не переходить на несуществующие статьи
if (!poostRepozitory.existsById(id)){
    return "redirect:/blog";
}

        model.addAttribute("title", "Блог");
//преобразуем в массив
        Optional<Poost> poost = poostRepozitory.findById(id);
        ArrayList<Poost> res = new ArrayList<>();
        poost.ifPresent(res::add);
        model.addAttribute("poost", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!poostRepozitory.existsById(id)){
            return "redirect:/blog";
        }
        model.addAttribute("title", "Блог");
        Optional<Poost> poost = poostRepozitory.findById(id);
        ArrayList<Poost> res = new ArrayList<>();
        poost.ifPresent(res::add);
        model.addAttribute("poost", res);
        return "blog-edit";
    }
//редактирование-сохранение
    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons,@RequestParam String full_text, Model model) {
        Poost poost = poostRepozitory.findById(id).orElseThrow(null);
        poost.setTitle(title);
        poost.setAnons(anons);
        poost.setFull_text(full_text);
        model.addAttribute("title", "Блог");
        //сохранить объект на основе класса пост
        poostRepozitory.save(poost);
        return "redirect:/blog";
    }
//удаление
    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Poost poost = poostRepozitory.findById(id).orElseThrow(null);

        model.addAttribute("title", "Блог");
        //сохранить объект на основе класса пост
        poostRepozitory.delete(poost);
        return "redirect:/blog";
    }

}
