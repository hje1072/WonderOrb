# WonderOrb

## 들어가기 앞서
###  API 키 설정
1. 프로젝트의 `app/src/main/assets/` 디렉터리에 `apikey_example.properties` 파일을 찾습니다.
2. `apikey_example.properties` 파일을 복사하여, `apikey.properties`로 이름을 변경합니다.
3. `apikey.properties` 파일에 OpenAI API 키를 입력:
   ```properties
   API_KEY=YOUR_ACTUAL_API_KEY
이후 저장해주면 됩니다.

##
**현재 구현된 기능**  
- 음성 녹음 및 텍스트로 변환  
- 녹음된 질문을 바탕으로 답변을 해줌.

   
# 프로젝트 개요  
WonderOrb는 사용자의 질문에 간단하고 유머러스한 답변을 제공하는 앱입니다.  
스폰지밥의 "마법의 소라고동"과 같이, 사용자에게 소소한 재미와 유쾌한 경험을 선사하는 것을 목표로 합니다.

## 주요 기능 및 사용 기술  
- **질문 답변 API**  
  사용자의 질문을 이해하고 적합한 답변을 생성합니다.  
- **답변 단순화 API**  
  추상적이고 유머러스한 답변을 제공하여 부담 없는 재미를 추구합니다.  
- **텍스트 음성 변환 (Text-to-Speech)**  
  생성된 답변을 음성으로 출력하여 생동감을 더합니다.  

## 적용된 API  


## 기대 효과  
- **킬링타임 및 아이스브레이킹**  
  대화에 재미를 더하거나 어색한 분위기를 풀어주는 도구로 활용할 수 있습니다.  
- **긴장감 완화**  
  유머러스한 답변을 통해 고민이나 긴장감을 완화할 수 있습니다.  

## 개발 환경  
- Android Studio (Java)  
- API Level: 28  
- Package Name: `com.example.wonderorb`  

## 향후 계획  
- 멀티언어 지원 (한국어/영어)  
- 질문 입력 기능 및 답변 생성 기능 통합  
- UI 개선 및 추가 기능 개발  
