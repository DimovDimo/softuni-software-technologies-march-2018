package cat.controller;

import cat.entity.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import cat.bindingModel.CatBindingModel;
import cat.repository.CatRepository;

import java.util.List;

@Controller
public class CatController {
    private final CatRepository catRepository;

    @Autowired
    public CatController(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @GetMapping("/")
    public String index(Model model) {

        List<Cat> cats = catRepository.findAll();
        model.addAttribute("view", "cat/index");
        model.addAttribute("cats", cats);

        return "base-layout";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("view", "cat/create");
        return "base-layout";
    }

    @PostMapping("/create")
    public String createProcess(Model model, CatBindingModel catBindingModel) {

        Cat film = new Cat(
                catBindingModel.getName(),
                catBindingModel.getNickname(),
                catBindingModel.getPrice()
        );

        this.catRepository.saveAndFlush(film);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id) {

        if (!this.catRepository.exists(id)){
            return "redirect:/";
        }

        Cat cat = catRepository.findOne(id);
        model.addAttribute("cat", cat);
        model.addAttribute("view", "cat/edit");
        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable int id, Model model, CatBindingModel catBindingModel) {

        if (!this.catRepository.exists(id)){
            return "redirect:/";
        }

        Cat film = catRepository.findOne(id);
        film.setName(catBindingModel.getName());
        film.setNickname(catBindingModel.getNickname());
        film.setPrice(catBindingModel.getPrice());
        this.catRepository.saveAndFlush(film);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        if (!this.catRepository.exists(id)){
            return "redirect:/";
        }

        Cat cat = catRepository.findOne(id);
        model.addAttribute("cat", cat);
        model.addAttribute("view", "cat/delete");
        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable int id, CatBindingModel catBindingModel) {

        Cat cat = catRepository.findOne(id);
        catRepository.delete(cat);

        return "redirect:/";
    }
}
