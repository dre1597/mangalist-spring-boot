package org.example.mangalistspringboot.view;

import org.example.mangalistspringboot.domain.helpers.SearchQuery;
import org.example.mangalistspringboot.infra.api.dto.requests.CreateMangaRequest;
import org.example.mangalistspringboot.usecases.DeleteMangaUseCase;
import org.example.mangalistspringboot.usecases.GetOneMangaUseCase;
import org.example.mangalistspringboot.usecases.ListMangasUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.UUID;

@Controller
public class ViewController {
  private final ListMangasUseCase listMangasUseCase;
  private final GetOneMangaUseCase getOneMangaUseCase;
  private final DeleteMangaUseCase deleteMangaUseCase;

  public ViewController(
      final ListMangasUseCase listMangasUseCase,
      final GetOneMangaUseCase getOneMangaUseCase,
      final DeleteMangaUseCase deleteMangaUseCase
  ) {
    this.listMangasUseCase = Objects.requireNonNull(listMangasUseCase);
    this.getOneMangaUseCase = Objects.requireNonNull(getOneMangaUseCase);
    this.deleteMangaUseCase = Objects.requireNonNull(deleteMangaUseCase);
  }

  @GetMapping("/")
  public ModelAndView list(@RequestParam(value = "terms", defaultValue = "") final String terms) {
    var model = new ModelAndView("index");
    var query = new SearchQuery(0, 10, terms, "name", "asc");
    model.addObject("mangas", listMangasUseCase.execute(query).items());
    model.addObject("terms", terms);

    return model;
  }

  @GetMapping("/create")
  public String create(final CreateMangaRequest manga) {
    return "create";
  }

  @GetMapping("/edit/{id}")
  public ModelAndView view(@PathVariable("id") final String id) {
    var model = new ModelAndView();
    model.setViewName("create");
    var manga = getOneMangaUseCase.execute(UUID.fromString(id));
    model.addObject("manga", manga);

    return model;
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") final String id) {
    deleteMangaUseCase.execute(UUID.fromString(id));
    return "redirect:/";
  }
}
