package com.galaxy.novelit.share.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditableReqDTO {
	private String directoryUUID;
	private boolean editable;
}
