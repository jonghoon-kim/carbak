package com.chabak.services;

import com.chabak.repositories.GenerateDataDao;
import com.chabak.util.Utility;
import com.chabak.vo.*;
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
                review.setTitle("타이틀"+i);
            }

            review.setSido("인천광역시");
            review.setGugun("계양구");

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
}
