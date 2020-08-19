package com.chabak.services;

import com.chabak.repositories.GenerateDataDao;
import com.chabak.util.Utility;
import com.chabak.vo.*;
import com.sun.org.apache.xpath.internal.operations.String;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenerateDataService {

    @Autowired
    MemberService memberService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReadCountService readCountService;
    @Autowired
    ReviewLikeService reviewLikeService;
    @Autowired
    GenerateDataDao generateDataDao;

    public int generateMemberData(int numberOfGenerate){
        Member member = new Member();
        try{
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
            String today;
            String createdString;
            List<String> idList = this.readIdFromTextFile();
            List<String> profileList = this.readProfileFromTextFile();

            //idList 크기만큼만 반복
            numberOfGenerate = idList.size();
            System.out.println("idList.size():"+idList.size());
            System.out.println("profileList size:"+profileList.size());

             for(int i=0;i<numberOfGenerate;i++){
//                today= formatter.format(new java.util.Date());
//                createdString = today+"-"+i;


                member.setId(idList.get(i));
                member.setName(idList.get(i));
                member.setPassword("1");
                if(i%2==0){
                    member.setGender("m");
                }
                else{
                    member.setGender("f");
                }

                member.setSido("");
                member.setGugun("");
                member.setEmail(idList.get(i)+"@naver.com");
                if(profileList.size()-1 >= i){
                    member.setSaveName(profileList.get(i));
                    member.setSavePath("/profileImages/");
                }
                else{
                    int index = i % profileList.size();
                    member.setSaveName(profileList.get(index));
                    member.setSavePath("/profileImages/");
                }


                System.out.println("member:"+member);

                Member getMember = memberService.getMember(member.getId());
                if(getMember==null){
                    memberService.insert(member);
                    Thread.sleep(100);
                }

            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return 1;

    }

    @SneakyThrows
    public int generateReviewData(int numberOfGenerate){
        Review review = new Review();
        List<Member> memberList = generateDataDao.getAllMember();
        int numberOfMember = memberList.size();
        int randomValue = 0;

        //파일에서 읽은 이미지 리스트
        List<String> imageNameList = this.readImageFromTextFile();
        //파일에서 읽은 리뷰 타이틀 리스트
        List<String> titleList = this.readTitleFromTextFile();

        System.out.println("imageNameList:"+imageNameList);
        for(int i=0;i<numberOfGenerate;i++){
            System.out.println("for i="+i);
            int sequenceNo = reviewService.selectReviewNo();
            randomValue = (int)(Math.random() * numberOfMember);
            review.setReviewNo(sequenceNo);
            review.setId(memberList.get(randomValue).getId());

            //리뷰 타이틀
            if(titleList.size()-1 >= i){
                review.setTitle(titleList.get(i));
            }
            else{
                review.setTitle("야영장 추천");
            }

            String[] sidoArray = {"",""};
            review.setSido("");
            review.setGugun("");

            //이미지
            if(imageNameList.size()-1 >= i){
                review.setContent("<p><img src='/resources/editor/upload/"+imageNameList.get(i)+"'>#여기는 야영장 #어디게</p>");
                review.setTitleImageSrc("/resources/editor/upload/"+imageNameList.get(i));
            }
            else{
//                review.setContent("<p></p>");
////                review.setTitleImageSrc("/resources/img/campsite/noImage1.png");
                int index = i % imageNameList.size();
                review.setContent("<p><img src='/resources/editor/upload/"+imageNameList.get(index)+"'>#여기는 야영장 #어디게</p>");
                review.setTitleImageSrc("/resources/editor/upload/"+imageNameList.get(index));
            }

            System.out.println("debug1");
            review.setReadCount(0);
            review.setLikeCount(0);
            review.setReplyCount(0);
            review.setRegDate(null);
            review.setModifyDate(null);
            reviewService.insertReview(review);
            Thread.sleep(100);

        }
        return 1;
    }

    @SneakyThrows
    public int generateReadCountData(int readCountRangeStart,int readCountRangeEnd){
        ReadCount readCount = new ReadCount();
        System.out.println("in test");
        List<Member> memberList = generateDataDao.getAllMember();
        int listCnt = reviewService.maxReviewCount(null,null,null,null);
        Pagination pagination = new Pagination(listCnt,1);
        List<Review> reviewList = reviewService.selectReviewList(null,null,null,null,null,pagination.getStartIndex(),pagination.getPageSize());
        int randomValue = 0;
        int count=0;

        for(Member member: memberList){
            ///
            readCount.setId(member.getId());


            for(Review review:reviewList){
                ///
                readCount.setReviewNo(review.getReviewNo());
                randomValue = (int)(Math.random() * readCountRangeEnd) + readCountRangeStart;
                if(randomValue !=0){  //랜덤 생성 숫자(특정 사용자의 조회수)가 0 이면 insert하지 않음
                    readCount.setReadCount(randomValue);

                    ReadCount checkReadCount = readCountService.selectReadCount(readCount);
                    if(checkReadCount==null){

                        generateDataDao.insertReadCountForTest(readCount);
                        Thread.sleep(100);
                        System.out.println("readCount:"+readCount);
                    }
                    else{
                        count++;
                        generateDataDao.updateReadCountForTest(readCount);
                        Thread.sleep(100);
                    }

                }

            }
        }
        System.out.println("generateReadCountData count :"+count);
        return 1;
    }

    @SneakyThrows
    public int generateReviewLikeData(double likeChance){
        int value = (int)(likeChance*100);
        System.out.println("likeChance*100:"+value);
        int randomValue = 0;
        List<ReadCount> readCountList = generateDataDao.selectAllReadCount();
        System.out.println("readCountList size:"+readCountList.size());
        for(ReadCount readCount:readCountList){
            randomValue = (int)(Math.random() * 100) + 1;
            if(randomValue <= value){
                System.out.print("hit :");
                ReviewLike reviewLike = new ReviewLike();
                reviewLike.setId(readCount.getId());
                reviewLike.setReviewNo(readCount.getReviewNo());
                System.out.println("parameter reviewLike:"+reviewLike);
                int flagReviewLike = reviewLikeService.checkReviewLike(reviewLike);
                System.out.println("checkReviewLike:"+flagReviewLike);
                if(flagReviewLike!=1){
                    System.out.println("like reviewLikeInsert");
                    reviewLikeService.insertReviewLike(reviewLike);
                    Thread.sleep(100);
                }

            }
            else{
                System.out.println("non like");
            }
            System.out.println("==============for===============");

        }
        return 1;
    }

    @SneakyThrows
    public List<String> readImageFromTextFile(){
        File file = new File("C:"+File.separator+"review"+File.separator+"reviewImage"+File.separator+"list.txt");
        List<String> imagePathList = new ArrayList<>();
        if(file.exists()) {
            System.out.println("FILE EXIST");
            BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
            String sLine = null;
            while( (sLine = inFile.readLine()) != null )
                imagePathList.add(sLine);
        }
        return imagePathList;
    }

    @SneakyThrows
    public List<String> readTitleFromTextFile(){
        File file = new File("C:"+File.separator+"review"+File.separator+"reviewTitle"+File.separator+"list.txt");
        List<String> titleList = new ArrayList<>();
        if(file.exists()) {
            System.out.println("FILE EXIST");
            BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
            String sLine = null;
            while( (sLine = inFile.readLine()) != null )
                titleList.add(sLine);
        }
        return titleList;
    }
    @SneakyThrows
    public List<String> readIdFromTextFile(){
        File file = new File("C:"+File.separator+"review"+File.separator+"id"+File.separator+"list.txt");
        List<String> idList = new ArrayList<>();
        if(file.exists()) {
            System.out.println("FILE EXIST");
            BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
            String sLine = null;
            while( (sLine = inFile.readLine()) != null )
                idList.add(sLine);
        }
        return idList;
    }

    @SneakyThrows
    public List<String> readProfileFromTextFile(){
        File file = new File("C:"+File.separator+"review"+File.separator+"profileList"+File.separator+"list.txt");
        List<String> profileList = new ArrayList<>();
        if(file.exists()) {
            System.out.println("FILE EXIST");
            BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
            String sLine = null;
            while( (sLine = inFile.readLine()) != null )
                profileList.add(sLine);
        }
        return profileList;
    }

    @SneakyThrows
    public List<String> randomSidoGugun(){

        String[] sido = {"서울특별시", "인천광역시", "대전광역시", "광주광역시", "대구광역시", "울산광역시", "부산광역시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주도"};


        String[] gugun0 = {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};
        String[] gugun1 = {"계양구", "남구", "남동구", "동구", "부평구", "서구", "연수구", "중구", "강화군", "옹진군"};
        String[] gugun2 = {"대덕구", "동구", "서구", "유성구", "중구"};
        String[] gugun3 = {"광산구", "남구", "동구", "북구", "서구"};
        String[] gugun4 = {"남구", "달서구", "동구", "북구", "서구", "수성구", "중구", "달성군"};
        String[] gugun5 = {"남구", "동구", "북구", "중구", "울주군"};
        String[] gugun6 = {"강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군"};
        String[] gugun7 = {"고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시", "가평군", "양평군", "여주군", "연천군"};
        String[] gugun8 = {"강릉시", "동해시", "삼척시", "속초시", "원주시", "춘천시", "태백시", "고성군", "양구군", "양양군", "영월군", "인제군", "정선군", "철원군", "평창군", "홍천군", "화천군", "횡성군"};
        String[] gugun9 = {"제천시", "청주시", "충주시", "괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "증평군", "진천군", "청원군"};
        String[] gugun10 = {"계룡시", "공주시", "논산시", "보령시", "서산시", "아산시", "천안시", "금산군", "당진군", "부여군", "서천군", "연기군", "예산군", "청양군", "태안군", "홍성군"};
        String[] gugun11 = {"군산시", "김제시", "남원시", "익산시", "전주시", "정읍시", "고창군", "무주군", "부안군", "순창군", "완주군", "임실군", "장수군", "진안군"};
        String[] gugun12 = {"광양시", "나주시", "목포시", "순천시", "여수시", "강진군", "고흥군", "곡성군", "구례군", "담양군", "무안군", "보성군", "신안군", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"};
        String[] gugun13 = {"경산시", "경주시", "구미시", "김천시", "문경시", "상주시", "안동시", "영주시", "영천시", "포항시", "고령군", "군위군", "봉화군", "성주군", "영덕군", "영양군", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군"};
        String[] gugun14 = {"거제시", "김해시", "마산시", "밀양시", "사천시", "양산시", "진주시", "진해시", "창원시", "통영시", "거창군", "고성군", "남해군", "산청군", "의령군", "창녕군", "하동군", "함안군", "함양군", "합천군"};
        String[] gugun15 = {"서귀포시", "제주시", "남제주군", "북제주군"};

        int sidoSize = sido.length;

        int randomIndex1 = (int)(Math.random() * sidoSize);
//        ("gugun"+randomIndex1)[1];
        return null;

    }
}
