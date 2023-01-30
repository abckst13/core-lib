package kr.aipeppers.pep.core.interceptor;

/**
 * 각 업무 프로젝트의 Controller 레이어에서,
 * 특정 업무 비즈니스 요건에 의하여, 컨트롤러 전처리/후처리 등의 인터셉팅이 필요한 경우를 위한 커스텀 인터페이스
 *
 * 일반적인 preHandle, postHandle, afterCompletion 처리 및
 * 특정 조건(미션API대상등) 에 의한 ConditionalPostHandle 후처리
 *
 *
 */
public interface ControllerInterceptable extends PreHandlable, PostHandlable, AfterCompletionable, ConditionalPostHandlable {

}
