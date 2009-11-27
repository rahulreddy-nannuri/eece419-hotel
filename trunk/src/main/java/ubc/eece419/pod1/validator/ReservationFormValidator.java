package ubc.eece419.pod1.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ubc.eece419.pod1.entity.PaymentInfo;
import ubc.eece419.pod1.formcommand.ReservationForm;
import ubc.eece419.pod1.validator.PaymentInfoValidator;

public class ReservationFormValidator implements Validator {
	private Validator delegate = new ReflectionEntityValidator<PaymentInfo>(PaymentInfo.class);
	private PaymentInfoValidator paymentInfoValidator = new PaymentInfoValidator();

	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return ReservationForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomType", "entityvalidator.nullable");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "checkInDate", "entityvalidator.nullable");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "checkOutDate", "entityvalidator.nullable");

		errors.pushNestedPath("paymentInfo");
		ValidationUtils.invokeValidator(paymentInfoValidator, ((ReservationForm) target).getPaymentInfo(), errors);
		errors.popNestedPath();
	}
}