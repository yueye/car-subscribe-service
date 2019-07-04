package com.sxsd.car.utils.http;

public class HttpStatus {

	// 1xx Informational    1xx信息类的
	//继续
	public static final String CONTINUE = "100"; //Continue
	//交换协议
	public static final String SWITCHING_PROTOCOLS = "101"; //Switching Protocols
	//正在处理中
	public static final String PROCESSING = "102"; //Processing

	public static final String CHECKPOINT = "103"; //Checkpoint

	// 2xx Success
	//成功
	public static final String OK = "200"; //OK
	// 创建
	public static final String CREATED = "201"; //Created
	//接受
	public static final String ACCEPTED = "202"; //Accepted
	
	public static final String NON_AUTHORITATIVE_INFORMATION = "203"; //Non-Authoritative Information
	
	public static final String NO_CONTENT = "204"; //No Content
	  
	public static final String RESET_CONTENT = "205"; //Reset Content
	
	public static final String PARTIAL_CONTENT = "206"; //Partial Content
	
	public static final String MULTI_STATUS = "207"; //Multi-Status
	
	public static final String ALREADY_REPORTED = "208"; //Already Reported
	  
	public static final String IM_USED = "226"; //IM Used

	// 3xx Redirection 重定向

	public static final String MULTIPLE_CHOICES = "300"; //Multiple Choices
	//永久移除掉了
	public static final String MOVED_PERMANENTLY = "301"; //Moved Permanently
	  
	public static final String FOUND = "302"; //Found
	  
	public static final String SEE_OTHER = "303"; //See Other
	
	public static final String NOT_MODIFIED = "304"; //Not Modified
	//暂时的重定向了
	public static final String TEMPORARY_REDIRECT = "307"; //Temporary Redirect
	  // 永久的重定向了
	public static final String PERMANENT_REDIRECT = "308"; //Permanent Redirect

	// --- 4xx Client Error --- 4xx 客户端错误

	public static final String BAD_REQUEST = "400"; //Bad Request
	//没有认证权限
	public static final String UNAUTHORIZED = "401"; //Unauthorized
	  
	public static final String PAYMENT_REQUIRED = "402"; //Payment Required
	//禁止
	public static final String FORBIDDEN = "403"; //Forbidden
	
	public static final String NOT_FOUND = "404"; //Not Found
	//方法不允许
	public static final String METHOD_NOT_ALLOWED = "405"; //Method Not Allowed
	
	public static final String NOT_ACCEPTABLE = "406"; //Not Acceptable
	 
	public static final String PROXY_AUTHENTICATION_REQUIRED = "407"; //Proxy Authentication Required
	//请求超时
	public static final String REQUEST_TIMEOUT = "408"; //Request Timeout
	// 冲突
	public static final String CONFLICT = "409"; //Conflict
	
	public static final String GONE = "410"; //Gone
	
	public static final String LENGTH_REQUIRED = "411"; //Length Required
	  
	public static final String PRECONDITION_FAILED = "412"; //Precondition Failed
	
	public static final String PAYLOAD_TOO_LARGE = "413"; //Payload Too Large
	
	public static final String REQUEST_ENTITY_TOO_LARGE = "413"; //Request Entity Too Large
	
	public static final String URI_TOO_LONG = "414"; //URI Too Long
	
	public static final String REQUEST_URI_TOO_LONG = "414"; //Request-URI Too Long
	  
	public static final String UNSUPPORTED_MEDIA_TYPE = "415"; //Unsupported Media Type
	  
	public static final String REQUESTED_RANGE_NOT_SATISFIABLE = "416"; //Requested range not satisfiable
	  
	public static final String EXPECTATION_FAILED = "417"; //Expectation Failed
	
	public static final String I_AM_A_TEAPOT = "418"; //I'm a teapot
	
	public static final String INSUFFICIENT_SPACE_ON_RESOURCE = "419"; //Insufficient Space On Resource
	
	public static final String METHOD_FAILURE = "420"; //Method Failure
	
	public static final String DESTINATION_LOCKED = "421"; //Destination Locked
	  
	public static final String UNPROCESSABLE_ENTITY = "422"; //Unprocessable Entity
	
	public static final String LOCKED = "423"; //Locked
	
	public static final String FAILED_DEPENDENCY = "424"; //Failed Dependency
	  
	public static final String UPGRADE_REQUIRED = "426"; //Upgrade Required
	  
	public static final String PRECONDITION_REQUIRED = "428"; //Precondition Required
	//
	public static final String TOO_MANY_REQUESTS = "429"; //Too Many Requests
	
	public static final String REQUEST_HEADER_FIELDS_TOO_LARGE = "431"; //Request Header Fields Too Large
	
	public static final String UNAVAILABLE_FOR_LEGAL_REASONS = "451"; //Unavailable For Legal Reasons

	public static final String INTERNAL_SERVER_ERROR = "500"; //Internal Server Error
	
	public static final String NOT_IMPLEMENTED = "501"; //Not Implemented
	
	public static final String BAD_GATEWAY = "502"; //Bad Gateway
	
	public static final String SERVICE_UNAVAILABLE = "503"; //Service Unavailable
	
	public static final String GATEWAY_TIMEOUT = "504"; //Gateway Timeout
	
	public static final String HTTP_VERSION_NOT_SUPPORTED = "505"; //HTTP Version not supported
	
	public static final String VARIANT_ALSO_NEGOTIATES = "506"; //Variant Also Negotiates
	
	public static final String INSUFFICIENT_STORAGE = "507"; //Insufficient Storage
	
	public static final String LOOP_DETECTED = "508"; //Loop Detected
	
	public static final String BANDWIDTH_LIMIT_EXCEEDED = "509"; //Bandwidth Limit Exceeded
	
	public static final String NOT_EXTENDED = "510"; //Not Extended
	
	public static final String NETWORK_AUTHENTICATION_REQUIRED = "511"; //Network Authentication Required
}