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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        //bindingResult에는  Item에 바인딩이 된 결과가 담긴다. 이게 이전에 errors역할을 해준다.
        //Map<String,String> errors= new HashMap<>();
        //BindingResult는 모델 애트리뷰트 바로 이후에 작성되어야한다.

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){ //아이템 이름에 글자가 없다면
            bindingResult.addError(new FieldError("item","itemName","상품 이름은 필수입니다"));
            //필드 단위의 에러는 이 객쳉에 적는다. 오브젝트명(모델에 담기는 이름), 필드명 이름, 메시지
        }

        if(item.getPrice()==null||item.getPrice()<1000||item.getPrice()>1000000){
           bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000 까지 허용합니다"));
        }

        if(item.getQuantity()==null|| item.getQuantity()>=9999) {
            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증.
        if(item.getPrice()!=null||item.getQuantity()!=null){
            int resultPrice=item.getPrice()*item.getQuantity();
            if(resultPrice<10000){
                bindingResult.addError(new ObjectError("item",
                        "\"가격 * 수량의 합은 10,000원 이상이어야 합니다. " + "현재 값 = \" + resultPrice"));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            //model.addAttribute("errors", errors);
            //모델에 담을 필요가 없다. bindingResult는 자동으로 뷰에 같이 넘어간다.
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item,BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item", "itemName",
                    item.getItemName(), false, null, null, "상품 이름은 필수입니다."));
            //여기서 rejectedValue 가 바로 오류 발생시 사용자 입력 값을 저장하는 필드다.
        }

        /** FieldError 매개변수들들
        * objectName : 오류가 발생한 객체 이름
         * field : 오류 필드
         * rejectedValue : 사용자가 입력한 값(거절된 값)
         * bindingFailure : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값 (데이터는 일단 잘 들어와서 우리는 false)
         * codes : 메시지 코드
         * arguments : 메시지에서 사용하는 인자
         * defaultMessage : 기본 오류 메시지
         */

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() >
                1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(),
                    false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            bindingResult.addError(new FieldError("item", "quantity",
                    item.getQuantity(), false, null, null, "수량은 최대 9,999 까지 허용합니다."));
        }
        //특정 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", null, null,
                        "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    } //이제 form에 기존에 넣었던 값들이 유지된다. 이전 V1에서는 안남았었다.

    //스프링의 바인딩 오류 처리
    //타입 오류로 바인딩에 실패하면 스프링은 FieldError 를 생성하면서 사용자가 입력한 값을 넣어둔다.
    //그리고 해당 오류를 BindingResult 에 담아서 컨트롤러를 호출한다. 따라서 타입 오류 같은 바인딩
    //실패시에도 사용자의 오류 메시지를 정상 출력할 수 있다.


    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item,BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item", "itemName",
                    item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() >
                1000000) {
            bindingResult.addError(new FieldError(bindingResult.getObjectName(), "price", item.getPrice(),
                    false, new String[]{"range.item.price"}, new Object[]{1000,1000000},null));
        }
        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            bindingResult.addError(new FieldError("item", "quantity",
                    item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999},null));
        }
        //codes : required.item.itemName 를 사용해서 메시지 코드를 지정한다. 메시지 코드는 하나가 아니라
        //배열로 여러 값을 전달할 수 있는데, 순서대로 매칭해서 처음 매칭되는 메시지가 사용된다.
        //arguments : Object[]{1000, 1000000} 를 사용해서 코드의 {0} , {1} 로 치환할 값을 전달한다.

        //특정 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000,resultPrice}, null));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            log.info("objectName={}", bindingResult.getObjectName()); //우리의경우 item
            log.info("target={}", bindingResult.getTarget()); //진짜 객체를 넣는다
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item,BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(!StringUtils.hasText(item.getItemName())){
            //bindingResult.addError(new FieldError("item", "itemName",
                   // item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));

            bindingResult.rejectValue("itemName","required");
            //필드의 이름과 에러코드 첫글자만 넣으면 된다. 이게 무슨일?
            //오류 메시지가 정상 출력된다. 그런데 errors.properties 에 있는 코드를 직접 입력하지 않았는데
            //어떻게 된 것일까? 결론적으로는 얘가 V3에서 한 일, 즉 에러를 생성해준다.
        }

        //ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName","required");
        //이렇게 ValidationUtils 사용도 가능 위와 동일

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() >
                1000000) {
           // bindingResult.addError(new FieldError(bindingResult.getObjectName(), "price", item.getPrice(),
                    //false, new String[]{"range.item.price"}, new Object[]{1000,1000000},null));
            bindingResult.rejectValue("price","range",new Object[]{1000,1000000},null);
        }

        /**
         * field : 오류 필드명
         * errorCode : 오류 코드(이 오류 코드는 메시지에 등록된 코드가 아니다. 뒤에서 설명할
         * messageResolver를 위한 오류 코드이다.)
         * errorArgs : 오류 메시지에서 {0} 을 치환하기 위한 값
         * defaultMessage : 오류 메시지를 찾을 수 없을 때 사용하는 기본 메시지
         */

        if (item.getQuantity() == null || item.getQuantity() > 10000) {
           // bindingResult.addError(new FieldError("item", "quantity",
             //       item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999},null));
            bindingResult.rejectValue("quantity","max",new Object[]{9999},null);
        }

        //특정 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000,resultPrice}, null));
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            log.info("objectName={}", bindingResult.getObjectName()); //우리의경우 item
            log.info("target={}", bindingResult.getTarget()); //진짜 객체를 넣는다
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    //BindingResult 가 제공하는 rejectValue() , reject() 를 사용하면 FieldError , ObjectError 를
    //직접 생성하지 않고, 깔끔하게 검증 오류를 다룰 수 있다. 리젝트는 오브젝트 리젝트밸류는 필드

    /**
     *
     * 정리
     * 1. rejectValue() 호출
     * 2. MessageCodesResolver 를 사용해서 검증 오류 코드로 메시지 코드들을 생성
     * 3. new FieldError() 를 생성하면서 메시지 코드들을 보관
     * 4. th:errors 에서 메시지 코드들로 메시지를 순서대로 메시지에서 찾고, 노출
     */


    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item,BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        itemValidator.validate(item,bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            log.info("objectName={}", bindingResult.getObjectName()); //우리의경우 item
            log.info("target={}", bindingResult.getTarget()); //진짜 객체를 넣는다
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

/**BindingResult
 * 스프링이 제공하는 검증 오류를 보관하는 객체이다. 검증 오류가 발생하면 여기에 보관하면 된다.
 * BindingResult 가 있으면 @ModelAttribute 에 데이터 바인딩 시 오류가 발생해도 컨트롤러가 호출된다!
 *
 * BindingResult 가 없으면 400 오류가 발생하면서 컨트롤러가 호출되지 않고, 오류 페이지로 이동한다.
 * BindingResult 가 있으면 오류 정보( FieldError )를 BindingResult 에 담아서 컨트롤러를 정상 호출한다.
 *
 * BindingResult에 검증 오류를 적용하는 3가지 방법
 *
 * 1. @ModelAttribute 의 객체에 타입 오류 등으로 바인딩이 실패하는 경우 스프링이 FieldError 생성해서 BindingResult 에 넣어준다. 즉 알아서 해준다,
 * 2. 개발자가 직접 넣어준다.
 * 3. Validator 사용
 *
 * BindingResult 는 검증할 대상 바로 다음에 와야한다. 순서가 중요하다. 예를 들어서 @ModelAttribute
 * Item item , 바로 다음에 BindingResult 가 와야 한다.
 * BindingResult 는 Model에 자동으로 포함된다
 */

