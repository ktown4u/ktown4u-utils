# 두 객체를 비교한다.
두 객체를 비교하여 markdown diff 포맷으로 차이를 확인한다.
```diff
---
lineItems:
- * quantity: 2
+ * quantity: 3
  product:
-     name: "coffee"
+     name: "latte"
-     price: 1000
+     price: 3000
* quantity: 1
  product:
-     name: "Learning Spring Boot 2.0"
+     name: "Learning Spring Boot 3.0"
    price: 30000

```