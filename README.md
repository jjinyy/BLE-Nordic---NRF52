# BLE-Nordic---NRF52
![image](https://user-images.githubusercontent.com/85280844/147716388-255d9437-72df-4389-88ed-9643d814edc3.png)
![image](https://user-images.githubusercontent.com/85280844/147716403-a5676eb6-9dd6-4aa9-812b-11b01dce7d5c.png)
![image](https://user-images.githubusercontent.com/85280844/147716416-3ed44935-0074-4aaa-948c-8bc140ffe85c.png)
![image](https://user-images.githubusercontent.com/85280844/147716436-3e32ccbd-060f-4c37-98a7-39a3ed185bf1.png)
![image](https://user-images.githubusercontent.com/85280844/147716471-59a0e9fe-f2b2-45c9-8969-afd81ea390d7.png)
![image](https://user-images.githubusercontent.com/85280844/147716495-87c50489-5d00-4ee0-9abf-3c7811db226a.png)


# 목적
- 웨어러블 기기를 사용하는 사용자들에게 유의미한 서비스를 제공하려면,
각각의 웨어러블 기기에서 제공하는 데이터가 하나로 통합되어 분석할 수 있어야 한다.

# 설계
1. 기본설계

![image](https://user-images.githubusercontent.com/85280844/147715845-f6996eeb-3c0d-4fc0-b453-8231db85329c.png)


2. 프로파일 구조

![image](https://user-images.githubusercontent.com/85280844/147715859-ae1b45a3-807f-4f13-b6ba-f6141a335034.png)


3. Use Case

![image](https://user-images.githubusercontent.com/85280844/147715870-8f4721bf-579c-406c-9bc2-6acf3ee6681a.png)


4. functional requirement
4-1. 데이터 사용 기능

![image](https://user-images.githubusercontent.com/85280844/147715892-1bbaf1d1-633d-45b5-b3bf-4dcf5d09a024.png)


4-1-1. Suquence Diagram

![image](https://user-images.githubusercontent.com/85280844/147715904-19132269-1fa7-4977-bb65-162e8646a1c5.png)
![image](https://user-images.githubusercontent.com/85280844/147715922-19aab3c9-d218-450f-8302-aa8ca3180de7.png)
![image](https://user-images.githubusercontent.com/85280844/147715930-440af6f3-c355-48e2-a968-75c8ab6e5d71.png)
![image](https://user-images.githubusercontent.com/85280844/147715942-1bd879c6-3ac6-4db3-837b-f3febfb314d3.png)


4-2. WD 관리기능

![image](https://user-images.githubusercontent.com/85280844/147715963-f30d5b90-e335-4b20-b22e-85e497f83207.png)


4-2-1. Suquence Diagram

![image](https://user-images.githubusercontent.com/85280844/147716027-08708764-5336-4ac0-8bc9-0a67e5e5845c.png)
![image](https://user-images.githubusercontent.com/85280844/147716039-514637d5-d612-459b-b208-b14d183e909e.png)



# 기능구현
![image](https://user-images.githubusercontent.com/85280844/147716510-df088463-e0d3-4a67-84dc-c18ce0c72269.png)
![image](https://user-images.githubusercontent.com/85280844/147716518-ccc4e513-a239-400f-8899-6365bc137963.png)


1. UI
- 홈
![image](https://user-images.githubusercontent.com/85280844/147716086-a6a96184-81f5-4290-9b08-62e2583fc76b.png)


- 기기 스캔 및 연결
![image](https://user-images.githubusercontent.com/85280844/147716090-429674a9-5eff-4d2b-81d4-dec880e47423.png)


- 데이터보기
![image](https://user-images.githubusercontent.com/85280844/147716096-3f32d7d9-1dda-458f-8f7d-94b37128aa78.png)



2. 하드웨어
- Nordic nRF52DK

![image](https://user-images.githubusercontent.com/85280844/147716133-0c1c27bd-c876-47a8-bab8-414787c28528.png)
![image](https://user-images.githubusercontent.com/85280844/147716147-ce9f69c4-f608-44b3-a0ea-f350a869c6be.png)


- 심박센서 연결

![image](https://user-images.githubusercontent.com/85280844/147716156-56b274ce-cd60-4f8a-80fb-8c87a9c6b929.png)
![image](https://user-images.githubusercontent.com/85280844/147716165-dad9a96a-5014-4ef3-8378-26abf8d852f1.png)


3. Visuallizaion
- 시각화 Diagram
![image](https://user-images.githubusercontent.com/85280844/147716177-3a3f1c66-2454-45b0-9ca2-29d85c509323.png)


- Point class
![image](https://user-images.githubusercontent.com/85280844/147716186-69a82b3d-9cb8-4ec7-a803-24581cc36bd1.png)


- Linegraph classDiagram
![image](https://user-images.githubusercontent.com/85280844/147716210-a0f37bb9-b666-4c65-abaf-feb8ae0d2c90.png)


- SelecttimeDataActivity ClassDiagram
![image](https://user-images.githubusercontent.com/85280844/147716218-ee0d0410-052a-4068-9b07-25b64bdcce55.png)


- RealtimeDataActivity ClassDiagram
![image](https://user-images.githubusercontent.com/85280844/147716231-1c2c0a57-ff29-4da5-969c-45862e67343e.png)



4. Bluetooth
- Bluetooth ClassDiagram
![image](https://user-images.githubusercontent.com/85280844/147716246-71fb6d8a-6381-4073-b87e-13417e48ebb5.png)


- multiconnect
BluetoothDevice # connectGatt ()를 한후 BluetoothGattCallback # onConnectionStateChange () 를 완료하고, 다른 기기 또한 이 과정을 반복하면서 진행해서 다중 연결을 한다. 기존의 함수에 새로운 변수들을 추가해서 각각의 함수에 접근할 때의 방법을 바꿔주었다.

![image](https://user-images.githubusercontent.com/85280844/147716254-5cdae4ae-45ed-4e49-8c1c-e38f73e8a961.png)



5. Database & CSV
- DB 모델링
![image](https://user-images.githubusercontent.com/85280844/147716294-f4aebc56-5771-4f79-8ed1-5fab7976b15a.png)


- CSV 모델링
![image](https://user-images.githubusercontent.com/85280844/147716308-a5018e32-e0cf-4412-bbc0-dd4df58ff4ee.png)


- Bit field
![image](https://user-images.githubusercontent.com/85280844/147716312-8e22d60b-e571-4874-9c1b-c9c0d4f662db.png)


6  Profile
- BLE GATT Profile
![image](https://user-images.githubusercontent.com/85280844/147716332-b4e6e828-71c0-45f2-9487-f5fc173980f5.png)


- 클래스 다이어그램
![image](https://user-images.githubusercontent.com/85280844/147716353-de19e461-4137-4df3-8a5b-ecab10c80a0e.png)



# 참고 (결과)
기기와 연결한 센서로 측정 / 실시간으로 값이 출력됨.

![image](https://user-images.githubusercontent.com/85280844/147716566-d2fcbf6e-0312-4d70-8b56-42b0f09d4d0a.png)
![image](https://user-images.githubusercontent.com/85280844/147716580-00d34a06-3349-4d68-a1e3-4a5e5626154a.png)


그래프 출력 / 다중연결
![image](https://user-images.githubusercontent.com/85280844/147716602-7f126d7c-89d2-40d6-88e5-ccf10eb8a1eb.png)
![image](https://user-images.githubusercontent.com/85280844/147716612-a807f8f4-4b00-455b-956e-8f23e22a6102.png)


멀티연결 그래프 출력
![image](https://user-images.githubusercontent.com/85280844/147716628-33d0ae7b-1832-43c7-bce2-c3fadd007b2b.png)

