package ubc.eece419.pod1.validator;

import org.joda.time.DateTime;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ubc.eece419.pod1.formcommand.ReservationForm;
import ubc.eece419.pod1.validator.PaymentInfoValidator;

public class ReservationFormValidator implements Validator {
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
		ReservationForm reservationForm = (ReservationForm) target;
		DateTime now = new DateTime();
		if (reservationForm.getPaymentInfo().getExpiryYear() < now.getYear()) {
			errors.rejectValue("expiryYear", "creditcard.expired");
		} else if (reservationForm.getPaymentInfo().getExpiryYear() == now.getYear() &&
				reservationForm.getPaymentInfo().getExpiryMonth() < now.getMonthOfYear()) {
			errors.rejectValue("expiryYear", "creditcard.expired");
		}

		ValidationUtils.invokeValidator(paymentInfoValidator, reservationForm.getPaymentInfo(), errors);
		errors.popNestedPath();
	}
}