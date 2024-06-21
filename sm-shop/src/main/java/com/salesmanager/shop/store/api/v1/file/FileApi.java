package com.salesmanager.shop.store.api.v1.file;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.store.controller.file.facade.FileFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "CommonFile management resource (CommonFile Management Api)" })
public class FileApi {
	@Inject
	private FileFacade fileFacade;
	
	
	@GetMapping(value = "/file/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "CommonFile Download", notes = "CommonFile ")
	public @ResponseBody byte[] get(@PathVariable(name = "id") int id,@ApiIgnore MerchantStore merchantStore) throws Exception {
		byte[] bytes = fileFacade.getById(id,merchantStore);
		return bytes;
	}
}
