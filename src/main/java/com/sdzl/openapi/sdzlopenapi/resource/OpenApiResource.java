package com.sdzl.openapi.sdzlopenapi.resource;

import com.sdzl.openapi.sdzlopenapi.anticorruption.TreatmentSummaryAntiCorruptionServiceImp;
import com.sdzl.openapi.sdzlopenapi.anticorruption.UserAntiCorruptionServiceImp;
import com.sdzl.openapi.sdzlopenapi.config.SysConfig;
import com.sdzl.openapi.sdzlopenapi.model.Login;
import com.sdzl.openapi.sdzlopenapi.model.User;
import com.sdzl.openapi.sdzlopenapi.model.VAISToken;
import com.sdzl.openapi.sdzlopenapi.model.treatmentsummary.TreatmentSummaryDto;
import com.sdzl.openapi.sdzlopenapi.util.AuthenticationUtil;
import com.sdzl.openapi.sdzlopenapi.vais.VAISHttpClient;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping(value = "/openapi",produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class OpenApiResource {
    private static String CHARSET_UTF8 = "UTF-8";

    @Autowired
    private SysConfig sysConfig;
    @Autowired
    private VAISHttpClient vaisHttpClient;
    @Autowired
    private TreatmentSummaryAntiCorruptionServiceImp treatmentSummaryAntiCorruptionServiceImp;
    @Autowired
    private UserAntiCorruptionServiceImp userAntiCorruptionServiceImp;
    @Autowired
    private AuthenticationUtil authenticationUtil;


    @ApiOperation(value = "login", httpMethod = "POST", notes = "login")
    @ApiResponses({
        @ApiResponse(code = 400, message = "请求参数不正确"),
        @ApiResponse(code = 401, message = "未认证，请先登录"),
        @ApiResponse(code = 500, message = "内部错误，请联系管理员")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Login> login(User user){
        VAISToken vaisToken = null;
        vaisToken = vaisHttpClient.getTokenFromVAIS(user.getUsername(), user.getPassword());
        user.setToken(vaisToken.getAccess_token());

        Login login = userAntiCorruptionServiceImp.login(user);

        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @ApiOperation(value = "查询患者的TreatmentSummary信息", httpMethod = "GET", notes = "查询患者的TreatmentSummary信息")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "id1", required = true, dataType = "String", example = "1100", value = "患者的ID1"),
    })
    @ApiResponses({
        @ApiResponse(code = 400, message = "请求参数不正确"),
        @ApiResponse(code = 401, message = "未认证，请先登录"),
        @ApiResponse(code = 500, message = "内部错误，请联系管理员")
    })
    @RequestMapping(value = "/treatmentsummary", method = RequestMethod.GET)
    public ResponseEntity<TreatmentSummaryDto> queryRTSummary(@RequestParam("id1") String id1) {
        log.info("Query RT summary by id1[{}].", id1);
        TreatmentSummaryDto dto = new TreatmentSummaryDto();
        if (authenticationUtil.initDefaultToken()) {
            dto = treatmentSummaryAntiCorruptionServiceImp.getApproveTxSummaryById1(id1).orElse(dto);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
