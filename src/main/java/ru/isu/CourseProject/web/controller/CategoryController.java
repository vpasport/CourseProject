package ru.isu.CourseProject.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.domain.model.Category;
import ru.isu.CourseProject.domain.repository.CategoryRepository;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String all( Model model ){
        model.addAttribute( "categories", categoryRepository.getAll() );

        return "categories/categories";
    }

    @CrossOrigin
    @RequestMapping( value = "/allJson", method = RequestMethod.GET )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody List<Category> getAll(){
        return categoryRepository.getAll();
    }

    /*
        CREATE CATEGORY
     */

    @RequestMapping( value = "/create", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create( Model model ){
        Category category = new Category();
        model.addAttribute( "category", category );

        return "categories/createCategory";
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(
            @Valid @ModelAttribute( "category" ) Category category,
            BindingResult errors, Model model
    ){
        if( errors.hasErrors() ) return "error";

        categoryRepository.save( category );

        return "redirect:all";
    }

    @CrossOrigin
    @RequestMapping( value = "/createJson", method = RequestMethod.POST )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody Category getByIdJson(
            @RequestParam( "id" ) Integer id
    ){
        return categoryRepository.getById( id );
    }

    /*
        GET CATEGORY BY NAME
     */

    @RequestMapping( value = "/categorybynameJson", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody Category getByNameJson(
            @RequestParam( "name" ) String name
    ){
        return categoryRepository.getByName( name );
    }

    @RequestMapping( value = "/categoriesnamesJson", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody List<String> getAllNameJson(){
        return categoryRepository.getAllNames();
    }

    /*
        DELETE BY ID
     */

    @RequestMapping( value = "/deletebyid", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteById(
            @RequestParam( "id" ) Integer id,
            Model model
    ){
        categoryRepository.deleteById( id );

        return "redirect:all";
    }
}
