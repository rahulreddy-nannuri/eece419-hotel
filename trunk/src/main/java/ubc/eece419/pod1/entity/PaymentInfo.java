package ubc.eece419.pod1.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class PaymentInfo {

	public static enum CreditCardType {
		Visa, MasterCard;
	}

	public static PaymentInfo samplePaymentInfo() {
		PaymentInfo info = new PaymentInfo();
		info.setCardType(CreditCardType.MasterCard);
		info.setCardNumber("1234-5678");
		info.setSecurityCode(777);
		info.setExpiryMonth(12);
		info.setExpiryYear(2012);
		return info;
	}

	private CreditCardType cardType;
	private String cardNumber;
	private Integer securityCode;
	private Integer expiryMonth;
	private Integer expiryYear;

	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	public CreditCardType getCardType() {
		return cardType;
	}

	public void setCardType(CreditCardType cardType) {
		this.cardType = cardType;
	}

	@Column(nullable=false)
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Column(nullable=false)
	public Integer getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(Integer securityCode) {
		this.securityCode = securityCode;
	}

	@Column(nullable=false)
	public Integer getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(Integer expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	@Column(nullable=false)
	public Integer getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(Integer expiryYear) {
		this.expiryYear = expiryYear;
	}

}
