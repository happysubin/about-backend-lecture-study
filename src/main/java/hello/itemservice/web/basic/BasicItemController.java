package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    /* 롬복으로 생략 가능
    //@Autowired 생성자 자동 주입, 생성자가 1개니 생략 가능
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
     */

    @GetMapping
    public String item(Model model){
        List<Item> items=itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId,Model model){
        Item item=itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam Integer quantity,
            Model model
    ){
        Item item=new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute(item);
        return "basic/item";
    }


    //@PostMapping("/add")
    public String addItemV2(
            @ModelAttribute("item") Item item){
        /* ModelAttribute가 자동으로 만들어줌
        @ModelAttribute 는 Item 객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해준다.
        Item item=new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        */
        itemRepository.save(item);

        //model.addAttribute(item); @ModelAttribute를 사용하면 자동으로 적용됨. 즉 생략 가능
        //그럼 매개변수로 등록할 이유도 없다. 인자로 안받아도 알아서 적용되기 때문
        return "basic/item";
    }
    //@ModelAttribute - 요청 파라미터 처리
    //@ModelAttribute - Model 추가   2가지 기능을 제공!!!!


    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        //ModelAttribute 이름 생략 이름을 안넣으면 Item이라는 클래스명을 맨 첫글자만 소문자로 바꿔서 모델로 등록
        //Item -> item
        itemRepository.save(item);
        return "basic/item";
    }


    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     */
    //@PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * PRG - Post/Redirect/Get
     */
    @PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item=itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 리다이렉트
     * 상품 수정은 마지막에 뷰 템플릿을 호출하는 대신에 상품 상세 화면으로 이동하도록 리다이렉트를 호출한다.
     * 스프링은 redirect:/... 으로 편리하게 리다이렉트를 지원한다.
     * redirect:/basic/items/{itemId}"
     * 컨트롤러에 매핑된 @PathVariable 의 값은 redirect 에도 사용 할 수 있다.
     * redirect:/basic/items/{itemId} {itemId} 는 @PathVariable Long itemId 의 값을
     * 그대로 사용한다.
     */

    //테스트용 데이터
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,100));
        itemRepository.save(new Item("itemB",20000,200));
    }
}
