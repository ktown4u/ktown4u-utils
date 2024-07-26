# 두 문자열을 비교한다.

두 문자열을 비교하여 markdown diff 포맷으로 차이를 확인한다.
```diff
- totalPrice: 0
+ totalPrice: 100
- totalDiscountPrice: 0
+ totalDiscountPrice: 10
- finalPrice: 0
+ finalPrice: 90
mileageToBeEarned: 0
- totalQty: 0
+ totalQty: 2
currency: "USD"
tube:
  name: ""
  price: 0
  qty: 0
  active: false
- items: []
+ items:
+ - productId:
+     shopNo: 164
+     productNo: 1
+     bundleNo: 2
+     fanClubProductNo: 3
+     eventNo: 4
+   qty: 2
+   name: "name"
+   image: "image"
+   retailPrice: 100
+   sellPrice: 90
+   artist: "artist"
+   brand: "brand"
+   saleStatusCode: "ON_SALE"
+   isAdultOnly: true
+   additionalItems: []

```