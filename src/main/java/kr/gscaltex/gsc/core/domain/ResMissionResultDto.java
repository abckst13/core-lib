package kr.gscaltex.gsc.core.domain;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResMissionResultDto {
	@NotNull
	@Schema(description = "미션제목명", example = "")
	private String misnTitNm;
	@Schema(description = "연결URL", example = "")
	private String connUrl;
	@NotNull
	@Schema(description = "미션달성여부", example = "")
	private String misnAchvYn;
}
