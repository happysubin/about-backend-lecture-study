package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);

    }
    @Override
    public void validate(Object target, Errors errors) {
        Item item=(Item)target;

        if(!StringUtils.hasText(item.getItemName())){
            errors.rejectValue("itemName","required");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() >
                1000000) {
            errors.rejectValue("price","range",new Object[]{1000,1000000},null);
        }

        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            errors.rejectValue("quantity","max",new Object[]{9999},null);
        }

        //특정 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin",new Object[]{10000,resultPrice}, null);
            }
        }
    }
}

//ItemValidator 를 스프링 빈으로 주입 받아서 직접 호출했다. 
//supports() {} : 해당 검증기를 지원하는 여부 확인(뒤에서 설명)
//validate(Object target, Errors errors) : 검증 대상 객체와 BindingResult