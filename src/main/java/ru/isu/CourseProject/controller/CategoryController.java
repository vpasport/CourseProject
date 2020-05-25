package ru.isu.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.model.Category;
import ru.isu.CourseProject.repository.CategoryRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping( "/categories" )
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    /*
        GET ALL CATEGORIES
     */

    @RequestMapping( value = "/all", method = RequestMethod.GET )
    public String all( Model model ){
        model.addAttribute( "categories", categoryRepository.getAll() );

        return "categories/categories";
    }

    @CrossOrigin
    @RequestMapping( value = "/allJson", method = RequestMethod.GET )
    public @ResponseBody List<Category> getAll(){
        return categoryRepository.getAll();
    }

    /*
        CREATE CATEGORY
     */

    @RequestMapping( value = "/create", method = RequestMethod.GET )
    public String create( Model model ){
        Category category = new Category();
        model.addAttribute( "category", category );

        return "categories/createCategory";
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST )
    public String create(
            @Valid @ModelAttribute( "category" ) Category category,
            BindingResult errors, Model model
    ){
        if( errors.hasErrors() ) return "index";

        categoryRepository.save( category );

        return "redirect:all";
    }

    @CrossOrigin
    @RequestMapping( value = "/createJson", method = RequestMethod.POST )
    public @ResponseBody String createJson(
            @RequestParam( "name" ) String name,
            @RequestParam( "description" ) String description
    ){
        Category category = new Category();

        if( categoryRepository.getByName( name ) == null ) category.setName( name );
        else return "{ status : false, error : 'name is busy' }";
        category.setDescription( description );

        categoryRepository.save( category );

        return String.format( "{ status : ok, id : %s }", categoryRepository.getByName( name ) );
    }

    /*
        GET CATEGORY BY ID
     */

    @RequestMapping( value = "/categorybyidJson", method = RequestMethod.GET )
    public @ResponseBody Category getByIdJson(
            @RequestParam( "id" ) Integer id
    ){
        return categoryRepository.getById( id );
    }

    /*
        GET CATEGORY BY NAME
     */

    @RequestMapping( value = "/categorybynameJson", method = RequestMethod.GET )
    public @ResponseBody Category getByNameJson(
            @RequestParam( "name" ) String name
    ){
        return categoryRepository.getByName( name );
    }

    @RequestMapping( value = "/categoriesnamesJson", method = RequestMethod.GET )
    public @ResponseBody List<String> getAllNameJson(){
        return categoryRepository.getAllNames();
    }
}
