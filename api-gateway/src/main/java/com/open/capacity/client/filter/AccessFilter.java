//package com.open.capacity.client.filter;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Component;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//
///**
// * Created by owen on 2017/9/10.
// */
//@Component
//public class AccessFilter extends ZuulFilter {
//
//     
//
//    @Override
//    public String filterType() {
//        return "pre";
//    }
//
//    @Override
//    public int filterOrder() {
//        return 0;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//       
//        try {
//        	
//        	//解决zuul token传递问题
////        	Authentication user = SecurityContextHolder.getContext()
////                    .getAuthentication();
////    		
////    		
////    		if(user!=null){
////    			
////    			if(user instanceof OAuth2Authentication){
////    				
////    				Authentication athentication = (Authentication)user;
////    				
////    				OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) athentication.getDetails() ;
////    				ctx.addZuulRequestHeader("Authorization", "Bearer "+details.getTokenValue());
////    			}
////    			
////    		}
//        	
//          
//        } catch (Exception e) {
//           
//        }
//        return null;
//    }
//
//     
//}
