package com.chabak.services;

import com.chabak.repositories.GenerateDataDao;
import com.chabak.vo.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void generateMemberData(int numberOfGenerate){
        Member member = new Member();
        try{
            for(int i=0;i<numberOfGenerate;i++){

                member.setId("id"+i);
                member.setName("name"+i);
                member.setPassword("1");
                member.setGender("m");
                member.setSido("");
                member.setGugun("");
                member.setName("name"+i);
                member.setEmail("email"+i);
                member.setSaveName("");
                member.setSavePath("");
                System.out.println("member:"+member);

                Member getMember = memberService.getMember(member.getId());
                if(getMember==null){
                    memberService.insert(member);
                }

            }

        }
        catch(Exception e){
            e.printStackTrace();
        }



    }

    public void generateReviewData(int numberOfGenerate){
        Review review = new Review();
        List<Member> memberList = generateDataDao.getAllMember();
        int numberOfMember = memberList.size();
        int randomValue = 0;
        for(int i=0;i<numberOfGenerate;i++){
            int sequenceNo = reviewService.getSequence();
            randomValue = (int)(Math.random() * numberOfMember);
            review.setReviewNo(sequenceNo);
            review.setId(memberList.get(randomValue).getId());
            review.setTitle("title"+sequenceNo);
            review.setSido("인천광역시");
            review.setGugun("계양구");
            review.setContent("content"+sequenceNo);
            review.setTitleImageSrc("");
            reviewService.insertReview(review);
        }


    }

    @SneakyThrows
    public void generateReadCountData(int readCountRangeStart,int readCountRangeEnd){
        ReadCount readCount = new ReadCount();
        System.out.println("in test");
        List<Member> memberList = generateDataDao.getAllMember();
        int listCnt = reviewService.maxReviewCount(null,null,null,null);
        Pagination pagination = new Pagination(listCnt,1);
        List<Review> reviewList = reviewService.selectReviewList(null,null,null,null,null,pagination.getStartIndex(),pagination.getPageSize());
        int randomValue = 0;

        for(Member member: memberList){
            ///
            readCount.setId(member.getId());


            for(Review review:reviewList){
                ///
                readCount.setReviewNo(review.getReviewNo());
                randomValue = (int)(Math.random() * readCountRangeEnd) + readCountRangeStart;
                readCount.setReadCount(randomValue);

                ReadCount checkReadCount = readCountService.selectReadCount(readCount);
                if(checkReadCount==null){
                    generateDataDao.insertReadCountForTest(readCount);
                    System.out.println("readCount:"+readCount);
                }

                generateDataDao.updateReadCountForTest(readCount);
            }
        }
    }

    public void generateReviewLikeData(double likeChance){
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
                }

            }
            else{
                System.out.println("non like");
            }
            System.out.println("==============for===============");

        }

    }

    public int updateReadCountForTest(ReadCount readCount){
        int updateCount = generateDataDao.updateReadCountForTest(readCount);
        return updateCount;
    }

    public List<Member> getAllMember(){
        return generateDataDao.getAllMember();
    }
}
