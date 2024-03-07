package com.kt.edu.thirdproject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FluxTest {

    @Test
    public void flux_just_consumer() {
        List<String> names = new ArrayList<String>();
        Flux<String> flux = Flux.just("자바","스칼라","파이썬").log();
        // 람다 표현식: 루프를 돌면서 데이터를 하나씩 꺼낸다는 의미
        flux.subscribe(s -> {
            System.out.println("시퀀스 수신 : " + s);
            names.add(s);
        });
        assertEquals(names, Arrays.asList("자바","스칼라","파이썬"));
    }

    @Test
    public void ArrayTest() {
        List<String> names = new ArrayList<>(Arrays.asList("자바", "스칼라", "파이썬"));
        for (String s : names) {
            System.out.println("시퀀스 수신 (For문) : " + s);
        }

        Iterator iter = names.iterator();
        while(iter.hasNext()){
            System.out.println("시퀀스 수신 (Iter) : " + iter.next());
        }
    }

    @Test
    public void ArrayStreamTest() {
        List<String> names = new ArrayList<>(Arrays.asList("자바", "스칼라", "파이썬"));
        //stream으로 구현
        names.stream().forEach(s -> System.out.println("시퀀스 수신 (Stream) : " + s));
    }

    @Test
    public void ArrayStreamFilterTest() {
        List<String> names = new ArrayList<>(Arrays.asList("자바", "스칼라", "파이썬"));
        //stream으로 구현
        names.stream()
                .filter(s -> "자바".equals(s)) //"자바".equals(s)와 s.equals("자바")는 동일한 결과
                .forEach(s -> System.out.println("시퀀스 수신 (Stream) : " + s));
    }

    @Test
    public void ArrayStreamFilterCollectTest() {
        List<String> names = new ArrayList<>(Arrays.asList("자바", "스칼라", "파이썬"));
        //stream으로 구현
       List<String> FilteredList =  names.stream()
               .filter(s -> "자바".equals(s)) //"자바"가 null이 아닌지 먼저 확인하고 동등성 검사. s가 null인 경우에도 NullPointerException 미발생
               .collect(Collectors.toList());
        System.out.println("FilteredList : " + FilteredList);
    }



    @Test
    public void ArrayStreamTest2() {
        List<Member> names = new ArrayList<>();
        names.add(new Member("자바", 20));
        names.add(new Member("스칼라", 30));
        names.add(new Member("파이썬", 40));

        // filter : 나이가 30 이상인 멤버들을 필터링하고, 나이만 출력 : 데이터 형태 가공 불가
        List<Member> filteredMembers = names.stream()
                .filter(s -> s.getAge() > 30)
                .collect(Collectors.toList());
        System.out.println("filteredMembers : " + filteredMembers);

        // map : 나이만 출력: 데이터 형태 가공 가능
        List<Integer> filteredAges = names.stream()
                .map(s -> s.getAge())
                .collect(Collectors.toList());
        System.out.println("filteredAges : " + filteredAges);

        // 이름만 출력
        List<String> nameList = names.stream()
                .map(Member::getName)
                .collect(Collectors.toList());
        System.out.println("nameList : " + nameList);
    }

    @Test
    public void SubscriberTest() {
        Flux.range(1, 3) // 1부터 3까지 세개의 이벤트를 발생시키는 Publisher
                .subscribe(new Subscriber<>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        System.out.println("[Subscriber] onSubscribe");
                        // subscription.request(3); // request 하지 않으면 onSubscribe 만 출력됨
                        subscription.request(Long.MAX_VALUE); // 최댓값을 찾아서 수행. request 는 괄호안에서 설정 가능. 단, 최댓값보다 적게 주면 onComplete 반환 안됨. 더 줄 것이 남아있어서.
                    }
                    @Override
                    public void onNext(Integer item) {
                        System.out.println("[Subscriber] onNext : " + item);
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("[Subscriber] onError : " + throwable.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        System.out.println("[Subscriber] onComplete");
                    }
                });
    }

    @Test
    public void flatMapMany(){
        Mono.just(1)
                .flatMapMany(item -> Flux.just(3, 2, 1))
                .subscribe(
                        item -> System.out.println("[Subscriber] onNext : " + item),
                        e -> System.out.println("[Subscriber] onError : " + e.getMessage()),
                        () -> System.out.println("[Subscriber] onComplete"));
    }

    @Test
    public void zip(){
        var flux1 = Flux.range(1, 15);
        var flux2 = Flux.range(1, 10).map(it -> it * 10);
        var flux3 = Flux.range(1, 5).map(it -> it * 100);
        Flux.zip(flux1, flux2, flux3)
                .subscribe(item -> System.out.println("[Subscriber] onNext : " + item),
                        e -> System.out.println("[Subscriber] onError : " + e.getMessage()),
                        () -> System.out.println("[Subscriber] onComplete"));
    }
}
@Data
@AllArgsConstructor
class Member {
    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}