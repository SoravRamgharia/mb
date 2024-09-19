package com.mb.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "errorResponse")
@Table(name = "errorResponse")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int reviewId;

	private Date timestamp;
	private String message;
	private String details;

    public ErrorResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

	@Override
	public String toString() {
		return "ErrorResponse [reviewId=" + reviewId + ", timestamp=" + timestamp + ", message=" + message
				+ ", details=" + details + "]";
	}

}
