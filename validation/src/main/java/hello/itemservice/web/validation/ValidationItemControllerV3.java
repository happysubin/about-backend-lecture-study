package hello.itemservice.web.validation;


import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        //그런데 실제 사용해보면 제약이 많고 복잡하다. 그리고 실무에서는 검증 기능이 해당 객체의 범위를
        //넘어서는 경우들도 종종 등장하는데, 그런 경우 대응이 어렵다.
        //따라서 오브젝트 오류(글로벌 오류)의 경우 @ScriptAssert 을 억지로 사용하는 것 보다는 다음과 같이
        //오브젝트 오류 관련 부분만 직접 자바 코드로 작성하는 것을 권장한다.

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000,
                        resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            log.info("objectName={}", bindingResult.getObjectName()); //우리의경우 item
            log.info("target={}", bindingResult.getTarget()); //진짜 객체를 넣는다
            return "validation/v3/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }
    //검증 순서
    //1. @ModelAttribute 각각의 필드에 타입 변환 시도
    //  1. 성공하면 다음으로
    //  2. 실패하면 typeMismatch 로 FieldError 추가
    //2. Validator 적용

    /**
     * 바인딩에 성공한 필드만 Bean Validation 적용
     * BeanValidator는 바인딩에 실패한 필드는 BeanValidation을 적용하지 않는다.
     * 생각해보면 타입 변환에 성공해서 바인딩에 성공한 필드여야 BeanValidation 적용이 의미 있다.
     * (일단 모델 객체에 바인딩 받는 값이 정상으로 들어와야 검증도 의미가 있다.)
     *
     * @ModelAttribute -> 각각의 필드 타입 변환시도 -> 변환에 성공한 필드만 BeanValidation 적용
     *
     * 예)
     * itemName 에 문자 "A" -> 입력 타입 변환 성공 -> itemName 필드에 BeanValidation 적용
     * price 에 문자 "A" 입력  -> "A"를 숫자 타입 변환 시도 실패 -> typeMismatch FieldError 추가
     *  -> price 필드는 BeanValidation 적용 X
     */


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

}

/**
 *스프링 MVC는 어떻게 Bean Validator를 사용?
 * 스프링 부트가 spring-boot-starter-validation 라이브러리를 넣으면 자동으로 Bean Validator를
 * 인지하고 스프링에 통합한다.
 *
 * 스프링 부트는 자동으로 글로벌 Validator로 등록한다.
 * LocalValidatorFactoryBean 을 글로벌 Validator로 등록한다. 이 Validator는 @NotNull 같은
 * 애노테이션을 보고 검증을 수행한다. 이렇게 글로벌 Validator가 적용되어 있기 때문에, @Valid ,
 * @Validated 만 적용하면 된다.
 * 검증 오류가 발생하면, FieldError , ObjectError 를 생성해서 BindingResult 에 담아준다.
 */