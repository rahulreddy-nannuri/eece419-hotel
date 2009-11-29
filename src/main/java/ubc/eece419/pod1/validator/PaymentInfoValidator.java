package ubc.eece419.pod1.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ubc.eece419.pod1.entity.PaymentInfo;

public class PaymentInfoValidator implements Validator {
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return PaymentInfo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cardholder", "entityvalidator.nullable");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cardType", "entityvalidator.nullable");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cardNumber", "entityvalidator.nullable");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "securityCode", "entityvalidator.nullable");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expiryMonth", "entityvalidator.nullable");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expiryYear", "entityvalidator.nullable");

		PaymentInfo paymentInfo = (PaymentInfo) target;
		String cardNumber = paymentInfo.getCardNumber();

		if(!cardNumber.matches("^4[0-9]{12}(?:[0-9]{3})?$") &&
		   !cardNumber.matches("^5[1-5][0-9]{14}$")) {
		   	errors.rejectValue("cardNumber", "paymentinfovalidator.invalidcardnumber");
		}
	}
}