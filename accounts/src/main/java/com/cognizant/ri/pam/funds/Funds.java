package com.cognizant.ri.pam.funds;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cognizant.ri.pam.accounts.Contribution.Allocation;

import lombok.Data;
import lombok.NoArgsConstructor;

@FeignClient("fund")
public interface Funds {
	
	
	@PostMapping("/funds/allocations")
	public AllocateMessage allocate(AllocateMessage message);
	
	@GetMapping("/funds")
	public Map<String, Integer> getFundValues(@RequestParam("id") Set<String> ids);
	
	
	@Data
	@NoArgsConstructor
	public static class AllocateMessage {
		String transactionId;
		LocalDateTime allocatedDate;
		List<FundAllocation> allocations;
	}
	
	@Data
	@NoArgsConstructor
	public static class FundAllocation {
		
		private String fundId;
		private String rowId;
		private BigDecimal amount;
		private BigDecimal unitPrice;
		private BigDecimal quantity;
		
		public FundAllocation(Allocation a) {
			this.fundId = a.getFundId();
			this.rowId = a.getPlanId();
			this.amount = a.getAmount();
		}
	}

}
