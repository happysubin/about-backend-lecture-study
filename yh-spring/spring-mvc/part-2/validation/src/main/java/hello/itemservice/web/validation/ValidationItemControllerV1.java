package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v1/items")
@RequiredArgsConstructor
public class ValidationItemControllerV1 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v1/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes,Model model) {
        //@ModelAttribute가 model.addAttribute("item",item) 을 실행해서 오류가 발생하고 페이지를 렌더링해도
        //폼에 기존에 입력했던 데이터가 남아있다. model을 이욯해서 재사용!!!

        //에러 메시지를 보관
        Map<String,String> errors= new HashMap<>();

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){ //아이템 이름에 글자가 없다면
            errors.put("itemName","상품 이름은 필수입니다");
        }

        if(item.getPrice()==null||item.getPrice()<1000||item.getPrice()>1000000){
            errors.put("price","가격은 1,000 ~ 1,000,000까지 허용합니다.");
        }

        if(item.getQuantity()==null|| item.getQuantity()>=9999) {
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
        }

        //특정 필드가 아닌 복합 룰 검증.
        if(item.getPrice()!=null||item.getQuantity()!=null){
            int resultPrice=item.getPrice()*item.getQuantity();
            if(resultPrice<10000){
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if(!errors.isEmpty()){
            log.info("errors = {}",errors);
            model.addAttribute("errors", errors);
            return "validation/v1/addForm";
        }


       Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v1/items/{itemId}";
    }

}

/**
 *
 * 뷰 템플릿에서 중복 처리가 많다. 뭔가 비슷하다.
 * 타입 오류 처리가 안된다. Item 의 price , quantity 같은 숫자 필드는 타입이 Integer 이므로 문자
 * 타입으로 설정하는 것이 불가능하다. 숫자 타입에 문자가 들어오면 오류가 발생한다. 그런데 이러한 오류는
 * 스프링MVC에서 컨트롤러에 진입하기도 전에 예외가 발생하기 때문에, 컨트롤러가 호출되지도 않고, 400
 * 예외가 발생하면서 오류 페이지를 띄워준다.
 * Item 의 price 에 문자를 입력하는 것 처럼 타입 오류가 발생해도 고객이 입력한 문자를 화면에 남겨야
 * 한다. 만약 컨트롤러가 호출된다고 가정해도 Item 의 price 는 Integer 이므로 문자를 보관할 수가 없다.
 * 결국 문자는 바인딩이 불가능하므로 고객이 입력한 문자가 사라지게 되고, 고객은 본인이 어떤 내용을
 * 입력해서 오류가 발생했는지 이해하기 어렵다.
 * 결국 고객이 입력한 값도 어딘가에 별도로 관리가 되어야 한다.
 */