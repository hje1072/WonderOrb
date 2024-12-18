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
- 보라색 화면에서는 자연어로 답변을 해줌. 초록색 화면에서는 이모지로만 아리송하게 답변을 해줌
- Another Fortune 버튼을 통해 모드 변경가능.




   
## Wonder Orb 
![image](https://github.com/user-attachments/assets/50a555d6-c57b-4c76-8b1a-d9008256df85)


WonderOrb는 사용자의 질문에 간단하고 유머러스한 답변을 제공하는 앱입니다.  
스폰지밥의 "마법의 소라고동"과 같이, 사용자에게 소소한 재미와 유쾌한 경험을 선사하는 것을 목표로 합니다.

---

## 2. 요약 (Executive Summary)

### 프로젝트의 목적  
Wonder Orb는 사용자 음성을 인식하고 적절한 답변을 제공하는 애플리케이션입니다. 단순한 무작위 답변이 아닌 **OpenAI GPT**를 활용해 유용하면서도 재미있는 답변을 제공합니다.

### 진행 내용  
1. 음성을 인식하고 이를 텍스트로 변환  
2. GPT API를 통해 재치 있고 의미 있는 답변 생성  
3. **서비스(Intent)** 방식을 적용하여 효율적으로 구현  

### 주요 결과와 성과  
Wonder Orb는 두 가지 모드로 구현되었습니다:  
1. **자연어 응답 모드**: 사용자의 질문에 자연스러운 언어로 답변  
2. **이모지 응답 모드**: 점성술과 같은 신비로운 느낌의 이모지 기반 답변  

이 두 가지 모드로 실용성과 재미 요소를 모두 갖춘 앱이 완성되었습니다.

---

## 3. 프로젝트 개요

### 목적  
Wonder Orb는 사용자의 질문에 간결하고 재치 있는 답변을 제공해 일상에 소소한 재미와 유쾌함을 선사합니다. 마치 **마법의 소라고동**이나 **운명의 책**처럼 특별한 경험을 목표로 합니다.

### 배경  
- **킬링타임 및 아이스브레이킹**: 친구들과의 대화에 재미를 더하거나 어색한 분위기 완화  
- **긴장감 완화**: 가벼운 답변을 통해 부담을 덜어주는 도구  

### 범위  
작업은 단계별로 나누어 진행한 후 통합되었습니다. (세부 구현 사항은 **Git Commit 기록** 참고)

---

## 4. 기술적 내용

### 사용한 API  
- **OpenAI GPT API**: 직관적이고 간단한 구현이 가능  

### 시스템 구조  
1. **메인 액티비티**  
   - 음성을 인식하여 텍스트화  
   - 텍스트를 서비스로 전달  
   - 서비스에서 받은 응답을 화면에 출력  

2. **서비스**  
   - 질문 텍스트를 정제 처리  
   - OpenAI GPT API에 전달  
   - API 응답을 메인 액티비티로 반환  

---

## 5. 결과 및 성과

### 결과물  
Wonder Orb는 기존의 **마법의 소라고동**처럼 신기한 답변을 제공하는 애플리케이션입니다.

### 기존 앱과의 차별점  
1. **기존 앱의 한계**  
   - 저장된 답변을 무작위로 출력  

2. **Wonder Orb의 혁신**  
   - GPT API를 통해 의미 있는 답변 제공  
   - 이모지 모드를 추가하여 점성술의 신비로운 경험 제공  

---

## 6. 시연 방법

### 기본 상태  

![스크린샷 2024-12-18 041304](https://github.com/user-attachments/assets/95be9e32-03c4-4877-88c3-fdfdc979e07e)
![스크린샷 2024-12-18 041328](https://github.com/user-attachments/assets/e436be48-f757-4245-8911-318979976ba3)

- **Another Fortune** 버튼을 통해 모드 변경 가능


  
https://github.com/user-attachments/assets/985226da-154e-49c9-9aa8-0be7ea1b0862

- 화면전환 시연 영상


### 질문 방법  
1. 구슬을 꾹 누른 상태에서 음성 인식 모드로 진입  
2. 원하는 질문 후 손을 떼면 답변이 출력됨  

---

### 시연 예시  

#### a. 보라색 수정구슬  
**질문**: "저녁으로 뭐 먹을까?"  ![스크린샷 2024-12-18 041653](https://github.com/user-attachments/assets/46b0b3ec-cdc0-49e9-99aa-b1ed0aad1c69)

**답변**: (예시 화면 출력)  ![스크린샷 2024-12-18 041715](https://github.com/user-attachments/assets/fed8dc3e-0ad0-4862-9612-337abd86e9e6)



https://github.com/user-attachments/assets/ed1074f0-b579-4389-bda9-7dc6c8196d53

- 시연영상 1: 저녁뭐먹지?

https://github.com/user-attachments/assets/d3d1356d-9702-4a8b-917c-6566a08a61a8

- 시연영상 2 : 친구랑 몇시에 만나지?



#### b. 초록색 수정구슬  
**질문**: "저녁으로 뭐 먹을까?"  ![스크린샷 2024-12-18 041932](https://github.com/user-attachments/assets/e3d1b417-ff13-4c34-be59-0aaf849e9754)

**답변**: (예시 화면 출력)  ![스크린샷 2024-12-18 041959](https://github.com/user-attachments/assets/d241853a-7348-4be6-b09c-d640d315d65d)




https://github.com/user-attachments/assets/febdf2fe-dcc1-43eb-a52f-e5f603dcd928

- 시연영상 1 : 저녁뭐먹지?



https://github.com/user-attachments/assets/9450b360-26fc-4f1b-88fd-19fb1b7ff271

-시연영상 2 : 친구랑 몇시에 만나지?



---
