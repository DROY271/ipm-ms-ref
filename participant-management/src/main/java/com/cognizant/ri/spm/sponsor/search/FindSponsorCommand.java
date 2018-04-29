package com.cognizant.ri.spm.sponsor.search;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FindSponsorCommand {
	private @NonNull String sponsorId;
}
