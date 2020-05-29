package ru.isu.CourseProject.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.domain.model.Category;
import ru.isu.CourseProject.domain.model.User;
import ru.isu.CourseProject.domain.repository.CategoryRepository;
import ru.isu.CourseProject.web.ConvertToJson;
import ru.isu.CourseProject.web.RoleChecker;

import javax.management.InvalidApplicationException;
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
    public @ResponseBody String getAll(
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( categoryRepository.getAll() );
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
    ) throws InvalidApplicationException {
        if( errors.hasErrors() ) throw new InvalidApplicationException( "kek" );

        categoryRepository.save( category );

        return "redirect:all";
    }

    @CrossOrigin
    @RequestMapping( value = "/createJson", method = RequestMethod.POST )
    public @ResponseBody String createJson(
            @RequestParam( "name" ) String name,
            @RequestParam( "description" ) String description,
            @RequestParam( "token" ) User user
    ){
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        Category category = new Category();

        if( categoryRepository.getByName( name ) == null ) category.setName( name );
        else return "{ status : false, error : 'name is busy' }";
        category.setDescription( description );

        categoryRepository.save( category );

        return String.format( "{ \"status\" : \"ok\", \"id\" : \"%s\" }", categoryRepository.getByName( name ) );
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

    /*
        EDIT CATEGORY BY ID
     */

    @RequestMapping( value = "/editcategory", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCategory(
            @RequestParam( "id" ) Integer id,
            Model model
    ){
        model.addAttribute( "category", categoryRepository.getById( id ) );

        return "categories/editCategory";
    }

    @RequestMapping( value = "/editcategory", method = RequestMethod.POST )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCategory(
            @Valid @ModelAttribute( "caterory" ) Category category,
            BindingResult errors,
            Model model
    ){
        if( errors.hasErrors() ) return "error";

        categoryRepository.save( category );
        return "redirect:all";
    }
}
