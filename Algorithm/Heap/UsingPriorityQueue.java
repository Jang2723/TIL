import java.util.PriorityQueue;

// Java의 우선순위 큐를 사용해보자.
public class UsingPriorityQueue {
    private static class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) {
/*        PriorityQueue<Integer> intPQueue
                = new PriorityQueue<>();*/
        PriorityQueue<Integer> intPQueue         // 큰 순으로 정렬
                = new PriorityQueue<>((o1, o2) -> o2 - o1);
        intPQueue.add(5);
        intPQueue.add(2);
        intPQueue.add(7);
        intPQueue.add(1);
        intPQueue.add(3);
        // 데이터를 제거하고 반환한다.
        Integer min = intPQueue.poll();
        System.out.println(min);

        // peek : 다음 데이터가 무엇인지 확인한다.
        Integer peekMin = intPQueue.peek();
        System.out.println(peekMin);

        // 나이 기준 정렬 (작은순으로)
        PriorityQueue<Person> personPQueue =
                new PriorityQueue<>((p1, p2) -> p1.age - p2.age);
        personPQueue.add(new Person("Alex", 30));
        personPQueue.add(new Person("Brad", 25));
        personPQueue.add(new Person("Chad", 36));
        personPQueue.add(new Person("Dave", 22));

        System.out.println(personPQueue.poll().name);
        System.out.println(personPQueue.poll().name);
        System.out.println(personPQueue.poll().name);


    }
}
