# ktown4u-utils

- ktown4u의 여러 프로젝트에서 공유되어 사용할 만한 유용한 기능을 제공하는 프로젝트

## PrettyJsonPrinter(v1.0.7)

- [approval test(Approvals.veriy)](https://approvaltests.com/)를 사용할 때 assert할 객체의 상태를 사람이 알아보기 쉬운 형태고 제공하는 printer를 매번
  수작업으로 작성할 수도 있지만
- ObjectMapper를 이용해서 간단히 printer의 효과를 낼 수 있도록 기능을 제공

```java

@Test
public void test() {
    Order order = OrderBuilder.anOrder()
            .orderLineItems(
                    OrderLineItemBuilder.anOrderLineItem()
                            .quantity(2L)
                            .product(ProductBuilder.aProduct()
                                    .name("coffee")
                                    .price("1000")
                            ),
                    OrderLineItemBuilder.anOrderLineItem()
                            .quantity(1L)
                            .product(ProductBuilder.aProduct()
                                    .name("Learning Spring Boot 2.0")
                                    .price("30000")
                            )
            )
            .build();

    Approvals.verify(com.ktown4u.utils.PrettyJsonPrinter.prettyPrint(order).stream()
            .filter(outLinesIncluding("id"))
            .filter(outLinesIncluding("description"))
            .collect(joining("\n"))
    );
}
```

- 위와 같이 verify에서 `PrettyJsonPrinter.prettyPrint`를 호출하고 테스트에서 검증하지 않으려는 컬럼들들 `outLinesIncluding` 함수를 이용해서 제거할 수 있음
- 위 테스트에서 생성된 검증 파일

```json
{
  "lineItems": [
    {
      "quantity": 2,
      "product": {
        "name": "coffee",
        "price": 1000
      }
    },
    {
      "quantity": 1,
      "product": {
        "name": "Learning Spring Boot 2.0",
        "price": 30000
      }
    }
  ]
}
```

## StreamUtils(v1.1.0)

- stream pipeline에서 자주 중복되는 기능이 사용됨

```java
class SteamUtilsTest {

    private Collection<User> sourceCollection;
    private String errorMessage;

    @BeforeEach
    void setUp() {
        sourceCollection = List.of(new User("msbaek", 1l), new User("msbaek2", 2l));
        errorMessage = "해당 사용자가 존재하지 않습니다.";
    }

    @DisplayName("컬렉션, Predicate를 제공하면 조건을 만족하는 첫번째 원소를 포함하는 Optional을 반환한다")
    @Test
    void getItem_returns_optional() {
        Optional<User> user = StreamUtils.getItem(sourceCollection, user1 -> user1.name().equals("msbaek"));
        user.map(User::name).ifPresentOrElse(
                name -> assertThat(name).isEqualTo("msbaek"),
                () -> assertThat(false).isTrue()
        );
    }

    @DisplayName("컬렉션, Predicate을 제공하면 Predicate을 만족하는 컬렉션의 원소가 없으면 예외를 발생시킨다")
    @Test
    void getItemOrElseThrow_throws_exception_whenNotFound() {
        assertThrows(IllegalArgumentException.class, () -> StreamUtils.getItemOrElseThrow(sourceCollection, user -> user.name().equals("msbaek3"), errorMessage));
    }

    @DisplayName("컬렉션, Predicate을 제공하면 Predicate을 만족하는 컬렉션의 원소를 반환한다")
    @Test
    void getItemOrElseThrow_returns_found_whenFound() {
        User user = StreamUtils.getItemOrElseThrow(sourceCollection, user1 -> user1.name().equals("msbaek"), errorMessage);
        assertThat(user).isEqualTo(new User("msbaek", 1l));
    }

    @DisplayName("컬렉션에서 Id 추출 Function를 적용해서 Id 컬렉션을 반환한다")
    @Test
    void fetchIds_returns_id_collection() {
        Collection<Long> ids = StreamUtils.fetchIds(sourceCollection, User::id);
        assertThat(ids).containsExactly(1l, 2l);
    }

    @DisplayName("fetchIds를 적용해서 얻은 Id 컬렉션으로 객체를 반환하는 fetch Function을 적용해서 얻은 객체 컬렉션을 반환한다")
    @Test
    void fetchItems_returns_items_collection() {
        Collection<Long> ids = StreamUtils.fetchIds(sourceCollection, User::id);
        Collection<User> users = StreamUtils.fetchItems(sourceCollection, User::id, this::listUsersBy);
        assertThat(users).containsExactly(new User("msbaek", 1l), new User("msbaek2", 2l));
    }

    private Collection<User> listUsersBy(Collection<Long> ids) {
        return sourceCollection.stream()
                .filter(user -> ids.contains(user.id()))
                .toList();
    }
}
```

- 위와 같이 사용할 수 있음
    - `StreamUtils.getItem`: 주어진 컬렉션에서 Predicate을 만족하는 첫번째 객체를 Optional로 반환
    - `StreamUtils.getItemOrElseThrow`: `getItem`을 호출해서 조건에 맞는 객체가 존재하지 않으면 `IllegalArgumentException`을 주어진 메시지로 발생시킴
    - `StreamUtils.fetchIds`: 주어진 컬렉션에 idExtractor를 적용해서 id 컬렉션을 반환
    - `StreamUtils.fetchItems`: `fetchIds`를 적용해서 얻은 id 컬렉션에 대헛 주어진 fetch 함수를 호출하여 원하는 객체 컬렉션을 반환

## YamlPrinter(v1.2.0)

- 사용법

```java

@Test
@DisplayName("print - 모든 필드를 YAML 포멧 문자열로 반환.")
void print() {
    final String result = YamlPrinter.print(order);

    Approvals.verify(result);
}

@Test
@DisplayName("printWithExclusions - 원하는 필드를 제외하고 YAML 포멧 문자열로 반환.")
void printWithExclusions() {
    final String[] filedNamesToExclude = {"id", "description"};
    final String result = YamlPrinter.printWithExclusions(order, filedNamesToExclude);

    Approvals.verify(result);
}
```

- approved.text 형식

```yaml
---
id: null
lineItems:
  - id: null
    quantity: 2
    product:
      id: null
      name: "Kenya AA Drip Coffee"
      description: "Bright, citrusy, with a hint of cocoa and a smooth finish."
      price: 8000
  - id: null
    quantity: 1
    product:
      id: null
      name: "Americano"
      description: "2-shot original blend Americano."
      price: 5000
```

## LineFormatter(V1.3.0)

- 사용법

```java

@DisplayName("name, value, columns를 입력하면 name과 value 사이에 columns만큼의 공백을 추가한 문자열을 반환한다")
@Test
void case0() {
    // given
    final String name = "name";
    final Object value = "value";

    // when
    final String result = lineFormatter.formatLineWithWhitespaces(name, value);

    // then
    Approvals.verify(result);
}
```

- approved.text 형식

```text
name value
```

## Neutralizer(v1.4.0)

- 테스트를 하다 보면 LocalDateTime처럼 호출할 때마다 변경되는 값이 포함된 결과를 검증해야 하는 경우가 있음
- 이럴 경우 이를 무력화(neutralize)하는 기능이 필요함

```java

@Test
void neutralizeLocalDateTime() {
    String string = "GoodsFamily{id=1, name='name', createdBy=1, updatedBy=null, createdAt=2021-08-01T00:00:00.000000, updatedAt=null, goodsFamily2Goods=[]}";

    String result = Neutralizer.localDateTime(string);

    assertThat(result).contains(Neutralizer.LOCAL_DATE_TIME_REPLACEMENT);
}
```

- 위와 같이 LocalDateTime을 toString()으로 변환한 값("2021-08-01T00:00:00.000000")을 `Neutralizer.LOCAL_DATE_TIME_REPLACEMENT`로 변환하는
  기능을 제공한다.
- 향후 LocalDateTime뿐만 아니라 다른 타입에 대해서도 제공할 예정

## LineFormatter with depth(v1.5.0)

- depth를 인자를 전달하면 depth만큼의 indentation 공백을 추가한 문자열을 반환한다

```java
@Test
void multiple_lines_with_depth() {
  LineFormatter lineFormatter = new LineFormatter(80);
  List<IndentiedLine> lines = List.of(
          new IndentiedLine(1, "key1", "value1"),
          new IndentiedLine(1, "key2", "value2"),
          new IndentiedLine(2, "key11", "value1"),
          new IndentiedLine(2, "key12", "value2")
  );
  String formattedLine = lineFormatter.formatLineWithWhitespaces("key", null);
  for (IndentiedLine line : lines) {
    formattedLine += lineFormatter.formatLineWithWhitespaces(line.depth, line.name, line.value);
  }
  Approvals.verify(formattedLine);
}

record IndentiedLine(int depth, String name, String value) {
}
```

- 이와 같이 호출하면 아래와 같은 결과를 얻을 수 있음

```text
key                                                                         null
    key1                                                                  value1
    key2                                                                  value2
        key11                                                             value1
        key12                                                             value2
```