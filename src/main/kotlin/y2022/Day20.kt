package y2022

import alsoPrintOnLines
import expecting
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    measureTime {
        val testInput = Day20(testInput)
        testInput.part1().expecting(3L)
//        testInput.part2().expecting(0L)

        val realInput = Day20(input)
        realInput.part1().expecting(7584L)
        realInput.part2().expecting(4907679608191L)
    }.also {
        println("Took ${it.inWholeSeconds} seconds or ${it.inWholeMilliseconds}ms.")
    }
}

class Day20(private val input: String) {

    fun part1(): Long {
        val lines = input.lines().mapIndexed { index, s -> index to s.toInt() }
        val newLines = lines.toMutableList()

        println(newLines)
        lines.forEach {
            val a = newLines.indexOf(it)
            newLines.removeAt(a)
            var newIndex = (a + it.second)
            while (newIndex < 0) newIndex += lines.size - 1
            while (newIndex > lines.size - 1) newIndex -= lines.size - 1
            if (newIndex == 0) newIndex = lines.size - 1
            newLines.add(newIndex, it)
            println("$newLines $it")
        }

        val indexOfZero = newLines.withIndex().filter { it.value.second == 0 }.map { it.index }.first()
        return listOf(
            newLines[(1000 + indexOfZero) % lines.size].second,
            newLines[(2000 + indexOfZero) % lines.size].second,
            newLines[(3000 + indexOfZero) % lines.size].second,
        ).sum().toLong()
    }

    fun part2(): Long {
        val lines = input.lines().mapIndexed { index, s -> index to s.toLong() * 811589153 }
        val newLines = lines.toMutableList()

        println(newLines)
        repeat(10) {
            lines.forEach {
                val a = newLines.indexOf(it)
                newLines.removeAt(a)
                var newIndex = (a + it.second)
                newIndex %= (lines.size - 1)
                while (newIndex < 0) newIndex += lines.size - 1
                newLines.add(newIndex.toInt(), it)
//                println("$newLines $it")
            }
            println("$it")
        }

        val indexOfZero = newLines.withIndex().filter { it.value.second == 0L }.map { it.index }.first()
        return listOf(
            newLines[(1000 + indexOfZero) % lines.size].second,
            newLines[(2000 + indexOfZero) % lines.size].second,
            newLines[(3000 + indexOfZero) % lines.size].second,
        ).sum().toLong()
        // 7584
    }
}


private val testInput =
    """
1
2
-3
3
-2
0
4
""".trimIndent()
private val input =
    """
3948
8349
-4882
-1065
9657
-7890
2902
4136
369
-2026
7899
6971
8465
-3249
1569
158
-7433
1502
-8019
5490
-9467
8375
5796
-2925
-5870
917
-67
9033
-2897
-1464
4789
-7483
-5
8954
1296
-9920
-5776
7037
-5211
-5785
179
-4519
340
-139
9417
9013
6984
-6111
-4459
6347
5516
-558
-7963
-1988
-3518
8174
-1819
-3340
8771
429
-9583
-7735
-121
-2672
4127
-2566
8332
-2793
195
5344
-106
-847
-1564
8469
-6412
-7046
5567
-9020
1994
4743
1867
-3394
5283
1867
-6807
6084
-5864
-8234
8017
-2794
-6541
1837
566
8052
-4578
9812
-1753
4336
-4044
662
-6598
-4381
5580
2240
-5474
-9511
-1758
-2156
-19
284
-9142
-8209
5167
-751
-3527
1815
-7227
-2428
650
2609
-237
-8950
5694
-1158
5720
3156
2478
-5596
-3879
4855
6150
-7890
-7913
-6805
-1204
-3987
8354
-1823
-3568
243
6289
-378
4524
766
1705
9159
3821
5211
6417
4109
5677
5719
5381
-6123
-9374
6354
1154
-2059
-1605
-3633
7635
-2454
-160
-6704
-4564
2655
-6419
-8677
-1240
8115
8642
-154
9749
8888
1329
-8404
-6029
-1876
-2258
-4155
-2045
-3175
-2232
9947
2013
-4411
-6620
-4876
-5058
-8970
8041
-3594
3662
-7413
5580
7464
-1691
-9831
-8115
-643
407
3799
2503
-4882
2107
-1747
7033
2177
1303
8042
3011
5283
-6744
-2361
-6759
7307
-6761
1554
9681
-4910
-1093
-2058
-8978
3682
5470
-9567
6055
-7927
-7312
4252
1561
-9838
595
7233
3130
-8996
2449
-7444
8116
2243
4565
4931
-2973
-9417
6744
-3249
-5952
1427
-986
-683
-6571
-4274
-1549
-8750
-9821
-1559
6503
7276
3420
-2966
-6885
5316
7411
-2091
-151
-2065
4252
-502
-2354
5034
-6353
1837
-8072
-4367
6828
-4811
6165
-9399
-3582
-7965
9289
-9810
-8439
-6646
-4902
5663
5121
2353
5550
3770
-1559
4643
1479
558
-4451
9257
6979
8590
-9800
-3256
-2184
-7147
5737
4551
-737
4034
-3659
-2045
-6305
7850
-8914
2347
4629
97
3729
8750
2226
8269
-516
-9521
-3123
-3388
-700
5292
-7850
-8721
-4774
7048
-857
-106
5493
-3082
1756
5937
-2360
818
1404
8493
6595
-3646
-5567
6515
-5782
2485
5625
6019
1482
-8316
3995
-7391
-424
-6572
-1158
-316
-8180
-3837
-7949
-26
8596
-7009
1790
-5321
-4183
6133
-5995
-8677
-1404
5977
-8206
-9882
2135
6329
-6357
674
-463
7411
5193
8338
-9522
-2806
-8115
-6419
-1564
208
-818
-237
7156
-5920
5582
465
9759
-8461
9658
9023
-5453
2404
9445
-6900
-3002
1251
6347
-8807
7169
757
8857
9702
-3014
-1315
-6335
-2684
917
2268
-1602
-1807
2547
-6617
-2460
8397
5150
-9399
4779
-7039
8232
651
495
2683
-9595
-9603
-880
8359
-2306
500
9974
-4642
-8790
-887
439
801
-9998
6899
-6419
5914
5887
9276
-6414
-7456
4832
4482
8269
-6980
5075
9103
-8926
-6426
5719
6328
6178
-3983
-4542
-953
-7653
9415
-5492
9364
-2078
-5579
2332
-3529
7088
-5525
-6599
-8881
5763
-5808
-9871
9413
-9671
-5595
-9291
8642
-3526
6342
654
3316
5567
-2914
-4487
7832
-4618
2658
336
-5384
-9121
-82
-4237
1869
1839
-9558
-6762
-5870
9964
8872
-5211
5704
8365
7722
1192
5961
-3847
-2368
2707
-298
7003
8180
-4200
-1814
9693
-7057
4819
639
-6020
7078
3797
7572
-4421
9699
4049
-7183
-1191
312
-7895
-1158
-863
5863
-5698
-5055
-757
5074
-6075
-9570
-1019
729
1939
5560
3497
-5253
-391
-9949
-9633
7300
-2823
-8695
3812
6971
-8298
-9255
-7070
-1396
5427
-9145
3358
-5301
5214
172
8833
-8699
4976
8035
-4754
-2546
-7276
2580
5490
-7854
2215
846
-4183
7236
-5171
-5006
-6707
5215
-7513
4881
-245
1363
-971
-843
437
5669
-3012
2440
5605
-8013
5118
704
-3001
9147
-8156
8624
-4696
5406
3432
3649
-3219
-8704
6243
4976
2066
9023
1573
3442
-4568
-7913
5229
8306
7402
-8175
8036
5984
-4742
-3566
-2896
5934
-8391
-2823
-7944
566
-6662
-9404
-1823
-9937
979
8155
-3659
-5921
-1483
-5891
442
8814
2547
-1267
-1209
-2305
-1242
-2315
-7845
1550
-2312
3100
-7508
891
-1454
-3277
9830
-7863
8436
-4748
-1166
6726
-1898
16
-1449
9639
-8251
-6948
3041
-109
-851
-4491
1205
7229
-2026
9907
-1142
-1809
5298
678
6321
5125
-2360
-4166
-4255
-5676
-4359
-4578
-8247
5607
8954
9008
2231
7463
3912
-3809
6559
-709
1908
5353
8960
-3705
-8690
5933
9983
729
676
9769
-5632
-749
-71
-9307
-3659
9637
-3803
-9315
-9765
-243
-7433
2118
7635
-158
-4702
-3726
6784
8793
4750
2951
5725
7633
-271
123
-6985
9720
6179
9508
-4540
-8156
2727
-177
5716
-5405
-92
-71
-8823
940
-9868
1589
9657
-1183
-7031
307
-2122
-9157
685
-7878
9172
-9009
3821
8116
-3361
-1752
920
-3483
9065
3632
-9463
250
-4501
2509
-4411
2664
-3929
2485
6642
8277
-8418
8349
3993
-8443
6270
-2688
703
-9642
8259
4772
179
9586
-7890
1672
-1090
6347
-6878
-5700
-4783
-4350
-4741
-7969
2302
-5225
-2545
-8431
-4690
-9267
-9821
-2808
4916
-4337
9804
-4107
6087
-965
7134
-8274
5785
8862
583
7842
-368
-8680
2796
9537
9316
-6006
-7343
9135
6687
3383
-3846
-1123
-1514
-8230
4218
-7994
-4295
6979
-5554
-2064
4750
7950
-9326
-7994
8738
-7787
9535
-2465
9531
-7556
5125
-6653
-4649
5888
2186
7205
358
-7185
-3659
-4291
-7913
-3448
-7319
2658
6347
-8368
278
5532
5446
-6262
-6736
-3569
-9258
-2162
-2908
-7641
-6131
-7453
-1727
3890
5542
78
-6284
-3880
6037
5571
-7982
-7591
0
5433
96
-7969
232
-761
-3846
522
-983
-8617
-7159
-303
1917
6900
-2388
5129
2522
7736
5860
-5032
7525
7162
-6464
-3406
6918
-9291
-3259
9453
-6558
-9518
240
-9102
2485
-6584
-516
-1510
-7806
4024
-583
9614
-3754
8233
3595
-2414
5504
-4282
8354
-4796
-6288
9138
-8484
-5471
-1478
-2898
-6305
-2908
2157
7373
-435
-9272
-132
-2561
7256
-5727
-7890
6379
-3199
-5199
-7193
-6118
6281
-4615
-730
4236
-7306
-9415
-4684
-8409
-6583
-6707
2978
-4555
-3194
-4269
-7894
-3971
1122
-9949
1917
1410
9090
6515
-1974
-752
8352
-4761
-374
-9281
-8315
-7369
3662
5932
-6167
-492
3326
-5211
9827
-818
-484
9636
2918
-9949
5702
-983
-3344
-1322
-8787
-3898
-551
6642
5525
-6763
-8748
-6571
1236
-6467
8227
3930
-2147
9586
7446
1034
6227
-1834
5169
9648
-1512
-879
9184
-4564
-8969
3383
-9995
3347
-5446
2564
8365
-4615
-3926
8679
-5765
3036
8562
-1711
5229
5380
-6846
4436
3607
-1431
2841
9823
-1626
4502
-4408
-8855
-9549
9827
-2790
-7193
8524
-8202
9544
-3918
-1476
3204
277
4111
5506
-8941
-4741
-6262
3440
3515
190
8403
7642
4024
-375
-9521
8573
9231
-7439
1586
-9210
9445
3579
4263
-1681
3613
-6721
-4145
3619
-9012
8605
5392
-2440
-303
6618
9097
-2445
-264
-978
-3194
2391
-3153
3067
-4796
5583
1097
-5890
-7370
7854
12
4313
-7177
-7787
-8315
-9931
-578
1427
8592
9659
-2409
4065
1310
7562
-2992
-424
6250
770
9396
3466
-483
-2540
6232
407
2050
-3226
7092
-7046
1095
9157
1205
-6997
7506
305
4961
-2145
7982
5560
-5194
-6929
-455
6233
9218
-1585
-5902
-147
-1267
6897
-5458
5224
-2245
-8256
9132
-3153
-7343
-379
3712
-9083
-5652
395
-5799
7988
1332
-7215
-3673
6753
-8254
-5971
-4371
1674
9214
-1017
-9227
7658
-3074
9219
7210
-6653
-4783
6146
-5478
-2977
6367
-3835
9878
-1697
3671
5615
8983
407
5344
-4883
1178
-9202
-148
-5409
1865
6670
-6477
-6408
-9331
-7179
8637
5342
1562
3632
-9456
4703
-8573
-2115
1218
5552
8062
-4686
-5907
-2935
-1801
-682
-2045
-4056
-1713
-4361
8414
6030
2634
-2695
3346
-8782
4280
6953
8487
5215
5651
-1868
-1327
-2363
5482
3420
-2629
1787
-9271
-6919
-3436
6619
8066
4335
-4690
1450
84
5846
2553
-5749
-5971
-5225
-5710
6572
6140
-6442
-7466
-7552
2514
-6563
-3788
2664
-8379
3758
-9248
-817
-5008
-1833
9848
4537
-5036
-5720
-1823
1634
1862
7047
-7028
-1442
6834
-3073
7201
182
-1736
-70
4757
-7302
4733
7321
8592
7222
1702
8425
-2719
-2584
-2303
2664
-1962
-7046
-2091
-7312
-9123
2393
3407
-1330
-9123
4597
-3928
7411
-5141
-3623
2965
9405
-5869
-7369
6019
9203
3297
9709
-419
-2859
-9712
3501
-127
-7444
583
-5766
-5494
9957
-4917
-574
-4797
4420
-7269
6076
7038
-5470
-3839
-4578
-5073
2627
-9240
4940
9892
5155
6160
6890
6160
9394
-806
4733
647
-5280
-1837
8817
2834
-519
-2399
6392
5313
-6281
3730
757
4409
-6279
4611
9054
-2994
-4434
177
5252
-5827
1831
-1697
8448
-2667
-2578
67
-5884
1352
-9726
4378
335
9658
-9304
7413
9262
-8999
-9257
2893
134
-3236
-319
-3623
-297
7417
7502
-5438
-6489
7721
8110
9275
1992
1539
351
3257
-3930
-3092
9759
-7520
-2005
9641
-7270
6232
-7372
-8368
846
4578
-8316
-5445
1011
7384
713
-2774
983
820
1836
1589
-1983
7789
5041
7843
6175
9195
3769
-3678
889
2379
5976
3500
-2360
344
2726
9191
-50
3869
4940
7013
-6572
1969
-3399
-8190
6785
9774
5567
4914
1212
3036
7337
1685
-7899
-4582
-121
-583
-6126
-7968
5265
-9517
-8669
-1248
-5911
8155
208
-3040
-2100
9442
9630
-5784
1014
9093
-6627
4631
-2540
-424
7282
-506
6745
5804
2182
-43
-683
7759
8740
1867
-8802
7662
4772
426
3383
537
7025
9598
-1729
384
-4761
-6172
-4866
-8782
3471
5232
5932
5215
1084
1933
-5884
-3663
-7630
4525
-9846
3150
6411
8984
7236
729
-3987
-971
-8117
3420
8962
995
-7106
-2918
7211
-863
8005
-8663
-6408
9591
-2704
-8646
6895
-8315
-406
9769
-1886
4935
5343
1220
-6728
6705
6623
-8836
7555
6910
-2355
2610
-6823
7998
-3951
6501
-324
-1936
-7942
8349
4429
-5788
-7565
8473
2710
-2695
4160
-1658
-755
7464
8533
1279
9352
-7247
2481
6957
-8428
-6630
5607
3492
3271
-2289
-4102
5392
6661
-7109
9700
-1802
8280
6940
8332
4881
-6831
-2592
7998
-4109
-9828
1097
-6296
-7969
-6855
16
5093
9887
8238
-9933
-7020
-7184
4077
1730
-8778
7304
-3076
1888
-9187
-4359
6087
-44
4679
-7043
5943
2380
2552
-1988
2643
-7658
7031
-5290
6313
-4025
6160
4905
-5618
9884
8056
7676
-1004
3919
1950
-4344
5839
1306
1987
4732
6317
-8401
6742
-2902
-4371
8110
5774
2841
-9890
-1772
9805
-9255
-8369
-7863
3776
996
-2258
-4946
722
8917
-2592
-7431
8139
-3138
7078
-5380
-1947
9357
9354
3953
7685
-8959
-9450
6904
-4157
6687
9162
-3867
-9976
-5375
-9404
-5183
9703
5382
-9616
4935
-1221
-6596
-7988
2546
-2369
-7976
-6526
-921
151
1798
-8145
344
-1743
9981
-3403
-8935
9214
4937
-1842
-2801
-1332
-7583
-3371
1929
7282
9123
-5587
2096
5935
-9220
-7179
-2433
-6077
-6667
-9673
914
2664
-1454
6453
4269
-7633
-5366
3205
4663
5766
-8784
9599
7182
-7946
-8667
-8224
-8334
6716
3064
3260
-23
-9159
-5600
5540
-5609
-8026
4034
-6754
2655
-7025
-5962
2018
5424
-5784
462
-7346
2140
5611
2856
4428
3632
6595
-7713
-3142
2148
1713
-9605
-1391
6540
-8529
368
-7358
-7393
2505
-3913
7633
-3987
6878
-7193
3445
-3710
6223
2457
9670
-6077
80
-9592
4679
-769
1829
8954
9565
-1206
7081
350
3794
-1067
-4331
1190
9671
1684
-2903
-5824
8935
-6773
7398
-4396
-2455
-5958
-2750
4878
7938
9721
3828
6597
-2518
4744
2335
6971
-1816
5691
7373
-8595
7205
8954
-7362
1220
-9570
3649
5823
-3449
-8707
-8597
2468
-1823
-4652
6522
-8211
-8706
394
-1067
4636
-2305
9879
-71
-4293
-3803
7742
-1093
-5686
-5123
-7032
2485
5433
-1514
80
-6721
4382
-9531
-3572
-8418
8043
-3101
-5052
4840
1755
8358
9194
-8965
55
-7224
-6508
6565
9669
-7102
-2670
2221
-4404
7066
1790
-9882
6113
-6763
4855
7335
-1561
4832
-2417
-2872
3258
-5788
-324
-6728
-7158
-145
-278
-5740
-6584
-1833
3295
-5141
-4615
-965
6738
-8676
1916
9619
4181
-7809
8335
7021
4026
1411
-3672
-2616
314
4482
4314
-3469
-8316
5580
3125
-9807
-5402
503
-5259
1194
1470
1589
9492
2568
-9744
-4761
-9309
-5857
4366
-6584
-9911
6037
3577
6817
-8379
7951
4843
-3719
6982
1295
7635
3740
-2056
1962
-1895
9878
540
-603
7397
-1166
5195
9405
-3678
7578
-9616
-8832
86
-7387
3911
-8628
-7927
-3544
2427
-9747
-9526
-5969
-4748
1530
-5463
-5194
9398
632
2152
-1278
6160
-8925
-6766
-29
-1729
3995
-3815
1200
4770
1956
-4690
-4145
2298
-9512
-7876
1578
4726
-7664
5607
5921
3525
535
-4188
3475
-9437
-590
-9835
-3176
1992
-405
1865
3011
-7799
-7787
1524
-3325
-5962
1194
-8375
5070
-9429
-2911
7813
-5811
6552
4776
9330
-4010
-8787
4211
9862
820
-5616
5852
-7538
7885
-155
-3258
5797
3714
-6512
-7171
5440
9948
3287
-9995
4501
-9758
-9726
313
7587
-8881
7375
-902
-6570
-7113
4732
-3219
8700
6043
-6857
-2750
-8823
7683
-9420
-1814
-7759
9357
2150
-1898
-689
-1154
943
1759
-5494
6324
305
-2526
-7683
6448
-9800
8417
6269
-9276
7517
2091
-7054
-8986
2267
4890
7751
1672
-1729
-7838
7494
-5280
-4372
-8654
190
3016
9417
-3904
-5432
-8618
5139
3110
5659
6199
3852
-4878
6321
8761
9431
3237
7010
-2621
-9532
-8730
-3893
5506
1051
2406
-6674
9645
-7441
1112
6960
-1142
-8726
2099
10
-8665
-2141
8406
-8724
6180
-7838
65
-2045
741
-463
4815
1856
-4418
-6281
-2535
-8060
439
4078
-6285
3560
-2613
9146
6927
-1792
-4470
6716
5290
-2156
-8365
-7969
6549
-4660
-1644
7956
2156
-6138
-9482
-4245
-1188
8712
-5445
7697
3851
-1271
-4998
3415
9470
9902
-4473
1042
-2451
-6176
-5453
3369
6006
-9404
-2375
-5539
2564
-547
-8925
3628
722
-4406
5969
2764
-4702
6726
-4396
-2005
9445
-9562
-7593
5093
-1584
5702
-4311
895
-297
-1558
-8247
-5544
6527
7909
-4797
4218
-8539
3912
9336
6089
4679
6347
-2518
-9332
-5023
-3198
4611
-1290
2668
7712
-5962
6776
7218
-2040
-8626
9439
-5058
-356
9628
-1740
-6420
9585
1965
-5781
8389
-2940
1569
-3735
-8035
-1981
6371
-8722
554
4968
7321
4570
-7253
5769
7413
-1720
9453
-7931
-2975
5137
-2948
5056
1627
-9607
1098
3211
-6703
7764
-5566
-683
-56
-8332
5770
5804
-6336
8738
9737
-2065
-1949
-8628
5852
-6081
-3193
1893
7707
-4649
2983
6001
3414
5257
-6567
-2167
-7974
3420
-4807
-3569
-5154
6950
3772
3492
3601
9630
3369
-4843
-9390
-3964
-5595
-202
-1369
-983
-7022
8744
-9840
-9884
-7335
-5073
-8628
-4898
740
-9276
-9010
2887
-8159
8473
7399
1863
-5160
-9761
9575
3613
-9532
2858
9257
-6736
-2361
3031
-2561
-6875
-3763
-9268
2105
-5993
1586
-6181
-8019
9115
-2063
5112
1875
4512
537
-6807
1450
-2638
3595
3909
3123
4341
6971
-8559
3105
8534
5538
-5194
-9128
7277
5693
-2360
-4992
3414
-6338
5698
-6126
6671
1176
-6637
1889
-2410
-8569
6898
2624
9123
8130
-3399
-1865
3473
6165
6864
4581
-8369
-5327
3980
-3055
7938
-1110
6664
-2821
7419
6642
6392
-3394
-3485
-1757
40
9535
-9197
-4364
3712
521
5099
-1896
-2859
8333
8112
-4706
-7221
-9255
6521
1781
7305
564
6584
-2215
3478
4842
2903
983
-8278
5424
-7466
-4449
8561
5865
65
-2938
-2522
-3485
-2865
8098
5737
315
-9331
-473
-3027
7817
2565
-7557
3233
-9893
-6906
1702
1864
9794
1385
8275
-784
4569
7337
-8864
1501
-4563
-5000
-127
-1931
-9311
5575
9097
102
-8677
-525
6340
9132
7250
-9549
-1879
-9995
-1981
5608
-885
3889
3467
38
5112
5669
-3625
4644
-4898
640
-4676
-1316
3794
-3716
-686
2832
-5763
9782
243
-8617
8906
5330
5722
-7809
-9713
2998
3082
9709
4364
9180
6173
4785
-6362
9399
6184
-8301
-4783
1956
-6318
7156
-4371
-887
2295
-7043
-5046
8891
9368
-4094
-1691
-8823
2042
566
-6282
2150
-5563
9687
1578
-1867
3876
5643
428
5115
5128
2722
-7516
-827
-6340
9919
-407
-7990
-5100
8071
8931
-3096
8818
4311
6857
-7634
1789
298
8198
1634
-8995
6843
2118
5655
3172
3478
-6542
3630
-3055
3953
-3855
5843
4181
-6175
-8719
8259
-5589
80
-910
9940
-1758
9361
-308
3107
-6078
-7793
-9627
7110
-9698
5414
2089
3071
-4380
9010
-538
-1558
-1132
-7085
8723
2678
4263
4629
-9589
7772
-9499
6578
-8787
-178
6941
7474
-4189
-193
-5439
5774
-6533
-6437
200
-4629
1173
4158
-7747
5702
-8409
9949
-7875
-1034
8034
-7528
-5579
1242
-9427
5948
2042
-1873
-5270
-8547
-9171
-6475
8944
9445
-8429
7782
-5059
565
-7043
7077
-5357
-4986
-3775
4110
4605
6728
-3091
-1232
-6929
-38
9448
969
5204
-4423
677
-4866
7727
3649
-4911
1213
2018
-2829
2914
-161
6179
-6119
-4721
687
9789
4796
3134
6490
518
5438
-1058
6712
-3456
5698
-3650
-9470
-5168
1236
6296
2229
757
6556
-1896
-4356
1250
-8428
3467
5580
7476
-1626
-5318
-6572
354
1239
-5418
4497
236
-817
-9367
4670
-7378
4349
-5453
-6757
3780
5225
-6240
3582
-9807
6766
1789
-8901
-965
6813
-3461
1486
-3483
6093
-1756
9101
-6006
-9331
4976
-7956
3819
3685
3456
-1584
-3357
-2334
2479
-340
6832
6367
-6893
-3278
7156
-7508
9417
-3550
-1785
-2422
9445
553
5173
-5942
-8408
-5579
-6305
-6239
-7830
5279
-2980
7974
-1686
-3494
-204
-5215
2776
-9008
-215
9727
-8367
6001
8847
8784
-4564
-2258
-119
-3988
4785
-4546
4842
-262
-3719
5943
-2289
-5957
-8005
-7297
-5045
1014
-2808
5091
-9859
-953
2978
-5800
-3095
4797
-7890
1385
5407
3889
5627
1790
-2375
570
-9716
8937
9470
8788
2954
3383
-9705
8794
3858
-6526
5438
-2966
-6006
-4463
-9500
1824
4922
9213
4309
-6703
6764
3456
9497
-8367
-2215
5171
5178
2084
-8725
5344
-2457
4092
-8411
-155
6113
-3814
3852
-8986
-1143
4252
-2633
7282
-4411
-499
-7086
1573
5431
6471
7985
177
-1758
-1084
7709
6617
-8529
1198
2108
1356
8213
-1280
-6296
4399
-9532
6028
-6547
-6598
3665
-856
4468
6246
9299
3140
-5960
6893
7894
4983
7397
1588
-2785
-7513
2481
261
1559
3100
-8734
-6509
7916
3968
-7001
-5824
-2312
-6754
9224
-2622
-9902
5542
4757
1750
-807
-2014
-9490
8111
-5438
-2540
5186
5942
8815
-4946
-4738
7894
3341
7602
3860
239
-6706
-3618
-9400
7056
8485
-7986
3368
4961
-1212
3361
-686
3725
7
-9560
-6834
-1922
-5189
-8247
-5154
4350
-9309
-1753
9886
9226
6527
-3682
-1114
-6675
8477
-3417
9666
1589
-4702
-7629
-3610
2839
-6952
6249
1933
-9219
-8817
5252
3042
-8595
1097
9020
-1514
-962
-6798
-9076
-8623
9415
9742
-9907
3471
-559
-5160
-1809
8949
2670
-6090
6128
1478
-546
6153
3632
-5795
-6194
-738
-7804
-5375
-9939
-8742
1390
-8619
-5060
-4465
6498
1940
-2788
-4789
1257
-4204
-7043
-8118
-6119
-2517
4754
-4441
-5600
7939
2607
7222
-3160
-9855
3883
-8274
-6474
-2345
-5141
-8327
6251
2393
3219
-7556
-797
8906
-8722
4036
1861
-4780
8478
-1697
-289
3415
-1697
9745
-3302
-4822
-4299
5641
1011
-5991
-5349
7731
9162
-9931
8961
5257
-499
-5457
703
-5882
7261
1269
2181
6603
8062
-3743
5375
9657
1510
-3648
-8779
2683
-152
9807
4438
1417
-7543
-5018
8070
-5211
-7717
4143
-8240
8332
5440
-3705
-2128
-7641
-2794
4050
-9414
6060
4429
1730
-9121
-1606
-1045
-4611
-6437
143
-7739
-4660
-8760
5510
6713
4560
-6198
-4719
7707
3930
-1114
-2162
-620
-3306
-8692
331
2064
439
-3831
-6271
-1139
-7020
-1549
-638
2856
-9288
-4770
1289
-5374
-2899
6960
-7267
9146
5169
-6834
-5920
-1534
-5525
5155
-82
-8677
8659
-1248
-5112
-3581
-3014
5121
8421
-5993
-5342
9233
-792
-4569
1656
-5435
-2148
-6344
210
-6831
-1068
-1358
-8428
-4100
3573
-1044
-3770
-9890
-2184
2742
5796
3819
1021
-7227
4743
-3212
1598
1054
9392
7945
-8705
5004
-8310
-857
-2732
-3249
-3279
-2145
-5596
3242
-9878
-9113
-5496
-4992
-2994
7066
-2703
-9252
-7747
9144
-5342
-632
-9932
-6037
6269
5597
-2443
-6576
-2306
9827
-2313
9290
-1974
-8893
3900
-2063
-5799
-2027
9188
-5438
9294
-2167
-6268
-4663
-7060
-6440
-3561
8355
2197
-3142
-2303
-2373
4080
7927
1630
-5860
4764
-6359
-9570
6137
8562
1356
-8677
8101
9782
-3056
2660
-9589
-1554
-1620
6454
-5376
-3073
-939
4560
4525
-4569
-9286
5571
4406
8983
-8965
1789
5232
4751
-9576
1652
-7282
6073
-9055
-8595
7198
5948
4754
-1834
5282
-637
-6837
-9954
9595
-8120
-6928
3297
4524
-1424
-2289
1555
4536
6699
2727
250
-1257
-3619
4689
-6291
9612
4771
1830
9392
595
6895
-4618
-6636
-9583
704
9281
3930
5952
-8286
-8795
3482
-3422
6032
9054
-7538
-1066
-7469
5353
8033
3948
2285
5362
-9312
5642
-9714
-2122
-287
-6913
8419
1406
5562
-8753
-6346
227
898
-6917
4732
-3933
-5189
3501
-4883
2854
7053
9365
-3185
1447
-1887
2048
-7595
-3249
783
-9621
-3770
6902
-6069
7776
3323
-3256
-5370
2634
-5398
-4807
9428
-3042
3995
1110
5760
-5755
-5903
1654
6129
-6118
-8056
328
3680
-9008
1523
2095
-9618
4940
-3031
-3516
-9485
2
6809
-3646
-3577
-9517
2074
-5446
-3764
-2180
6173
-2147
-8415
-1090
-2559
-5042
-3401
7550
407
-8415
-484
-6900
6777
-2306
82
3072
-2655
1756
-7739
-16
-8823
8580
-9945
6997
6626
2918
-9960
3503
9900
3676
-6426
6704
-3918
7010
-2829
-9946
-359
6249
-5539
-158
-3096
2697
-3238
2112
-7577
6681
7876
-9207
9417
-9719
6191
-6562
-3997
6281
2796
6537
-5685
-958
-6632
-5652
-3933
-5902
721
3386
-1108
7006
-1123
2056
-7738
-5197
-9085
-9415
222
-7230
-3555
5562
172
7902
-8315
9995
133
8622
9424
-3283
-1526
-8987
-9284
-5649
-7246
-4150
-4006
-3942
7024
2155
354
1667
-3690
566
-5806
-5253
-6478
7950
-7865
-9004
7782
-8812
-7388
1573
-820
-264
-6791
-3839
-5733
2464
-3594
6941
5974
2947
8417
2396
4759
-1052
3569
-6469
7050
5556
3275
53
-3277
-5369
8377
7923
-4025
-6706
5580
2415
1100
3020
-2459
-3403
-8430
7333
9144
1689
-5832
2053
1836
-6126
-2872
4312
2834
2259
6251
749
-6138
-3105
261
3515
5605
-2471
-5888
-8178
3919
6687
5417
-2147
-357
3960
-9239
-5727
2999
7211
-6947
-1146
1861
-9932
-547
-1270
1415
-2740
-4903
-5755
-7022
-7109
-8918
-5340
-1564
-4404
-336
4524
-2841
-8167
5937
-4553
5246
5749
4933
7593
9531
-8881
7515
3142
-1697
6433
-1772
9222
5482
1161
-7493
9352
4421
462
5339
-3267
-3551
-5884
7307
-1763
-8458
-3790
-4829
9336
4504
5276
-3035
-443
-6226
195
-2954
-8823
-1132
5282
-9542
-8903
261
5201
-7610
7958
-1833
8588
1872
8205
-8881
351
3257
6555
-4228
-246
-3672
-7396
4456
9972
6455
-5281
-2560
6147
6605
-5277
-6010
-3718
-5438
63
8008
2283
-5398
8441
7463
-8231
-8462
-5310
7707
9628
595
6357
4366
-4282
3936
-1240
9395
-3002
-5469
-8875
-4179
1743
6616
9898
5479
-3475
-8245
1814
-4022
9585
5382
2207
-9388
6364
-924
-9146
752
-5295
-3963
-7675
3878
-2975
9248
2711
96
-3002
-1239
-7468
-3545
-7212
-2368
-5180
-715
-148
4181
1559
-4212
-2373
-4780
4870
2631
4062
9598
9157
-5477
7751
4890
4569
6658
7721
-1681
1864
9577
3331
2006
3726
-8267
-6346
5007
3224
109
-7649
3463
3597
8346
6808
-7693
4252
-3303
-115
4059
6179
4785
-9713
-258
-2625
2552
-5032
238
5108
354
8954
4708
9065
6606
9222
5796
-2045
7910
7397
2277
-1923
550
-1403
-2045
2140
2573
-9090
-5023
6985
-5769
7031
-1590
7980
-6281
7939
-567
2285
-1927
8893
-2420
-7817
-3672
-303
2156
7432
3493
-4992
4287
-7799
-2760
-1584
1864
-3818
663
-2257
-3754
-2963
7756
-5718
7411
-1475
-3933
4561
-7546
-4511
4733
10
-6184
2140
1869
5630
349
-5097
-4815
-2205
6180
4110
-507
-5665
1259
7662
-574
-3361
-9532
5363
-1387
-2503
-4681
-4535
7947
9968
-7147
5567
3398
-4103
4663
1594
2229
-7098
-6007
2697
-6744
151
-9779
8017
1448
278
407
1867
-1895
-1880
9639
-5717
4733
-9720
1603
-7401
7713
-6565
1789
2495
-1068
9178
-9105
-3900
9804
-2465
-5310
-2518
8642
6039
1864
1154
-9963
-9130
-575
891
9289
-1586
261
-3807
8354
407
-2776
4726
2750
729
7549
3847
-5474
7692
9073
-6291
265
-2634
-857
-4754
4570
6813
3020
-2994
-341
-4380
-9645
-6006
287
-7956
8411
-4660
914
-9623
9093
-6779
-7090
-7368
1303
9478
-3534
-1328
-551
5957
7510
7123
9887
-5971
7974
1366
2946
1965
-2920
-8993
9573
-148
175
3101
7525
4515
-5078
8830
-3828
-4535
1094
2706
-2963
-7933
-1007
2988
9188
-2808
-5056
6597
-7605
-5683
3034
-8745
-1691
-3465
-7465
-8816
9329
-6414
-5519
-1837
-7974
-1914
7211
743
-2849
5625
3178
-2606
5257
-4156
-303
-7944
8143
1958
8628
-4423
-5971
-4726
5210
8115
1918
6449
7431
-1622
-264
-8871
-2329
1110
372
-8298
2501
-5525
-9123
-8496
-4153
4519
7910
8061
3050
2627
7303
9711
6734
5640
-8871
-6850
-1266
4312
5615
-8987
2687
-3018
-9426
7954
-879
-5427
-6669
-6721
8437
-2063
-5315
-2863
7323
4757
2903
9364
2903
-823
6449
-6118
440
8332
-924
-4171
-7863
-5110
8002
5804
-7469
1176
-2410
9543
-8220
-1144
-9855
-2246
4565
-4660
-7876
9353
1310
-8965
4167
7856
-2414
-4204
4961
6232
1730
7520
-2258
-7556
8483
2336
-2169
6736
-4629
-5565
446
-6258
9357
-2361
-3387
8623
-8116
-5375
-5854
3680
-5269
-359
-3315
7081
-7103
2099
-682
1688
-6110
7683
-5701
-536
-3174
-9272
1021
3050
7092
4715
3515
542
-3286
-8461
-7713
3418
-5310
7760
7505
1956
6764
-5349
7066
7397
-7168
-1457
-2545
-6432
-9068
410
-6591
-1403
-1553
-4385
6296
-802
-779
-6181
9129
-5006
4768
6371
-6078
-5207
8299
5674
-7613
-7147
-8338
-6784
1939
-3395
5774
1892
6886
-2026
-9088
-4285
-6175
8403
7150
5291
-4864
-4542
9023
-1340
-3943
2177
8211
1939
-3433
-6425
-7974
-7088
8297
-4872
232
-1692
-1896
7927
8155
2635
4528
-148
3613
-6576
-5702
-8224
4399
8770
-1823
-4911
8349
-890
-574
9957
5770
-8969
5896
3456
-1271
5642
-826
6916
6113
-3193
-6340
-8367
-1382
1326
-1336
-6589
4955
-7130
-1442
-4056
-895
-6061
2791
3571
-2293
-2621
-8272
7328
-5803
-9161
-9819
1273
5490
-9080
6603
1479
-6336
5716
7256
7244
5744
-9737
-4999
-6834
9090
3869
1167
-8364
-4924
1557
4398
-411
-9670
-6893
-3050
6843
3013
-45
-9858
-1492
-6890
-6094
4699
4542
-3358
-2489
8605
4311
846
5352
7502
-4803
-892
-1863
2923
1040
-4483
-7204
2664
-1418
1676
-6282
-3388
-9899
-8971
-8186
-4145
3374
3858
-463
-9914
-1894
-3139
-7183
-5280
5235
8772
-1404
3589
-2971
-7080
5672
-2360
1486
-9772
5821
5409
-8397
5681
6623
9928
-5391
7335
1014
6799
2512
-3243
9284
1022
-8235
-4910
4731
-5280
-9004
-5040
7677
-8677
-4997
-2030
4050
-4293
4018
-6121
-1301
-2417
6292
111
-8406
2697
671
9901
-8480
6140
2802
-2058
3566
-6422
-6692
-920
8238
2170
335
566
-1191
-8528
7164
-5854
-721
-2733
-7386
-4381
-3668
-965
2551
2570
-8072
2314
-8430
-2703
3031
722
-1300
-8795
3120
-5475
-6230
2750
8486
-8058
-6245
-6583
-264
7466
-9256
-4961
9206
287
9136
5860
-4031
-838
4251
339
-9790
-498
7550
-7021
5842
6079
7549
-3353
-668
-3964
6379
4947
7998
-7013
-9838
-6075
3011
700
1339
8200
5567
6708
2268
1133
3819
5974
5405
2361
-2684
-6147
-8163
-5400
-7601
5462
4192
-5123
-3624
927
8925
969
-5881
-6652
-6206
-3987
-8055
7832
8116
3369
5741
8602
495
6945
-9327
-4196
-1319
8286
7552
-2687
9291
354
6979
-5620
6237
261
3211
6165
-4636
-9914
-2257
7562
9148
-3063
-6725
-2965
1527
-5618
806
-8480
-3951
3712
-7920
1284
-5326
5932
8332
9321
-502
-6586
-8184
-4040
3393
219
4759
-9336
-582
-289
-8387
444
503
-6806
-4676
-2692
8930
-9130
305
5186
1868
-9057
-9020
-4769
6503
-6831
-4903
-7789
-7247
1122
7110
-7282
-3929
-7567
-4183
4236
2697
3260
-559
2097
2332
-1575
-219
-6627
8679
-3417
4906
5787
-951
-5382
-9004
-4535
-3865
9352
2977
-1657
-3175
8814
-9855
917
-9855
7136
2650
-8852
-5869
-9121
176
4978
1702
-8971
-6478
3059
-7875
-3773
305
-9020
980
-897
5737
-6450
-6021
-3120
7555
9411
-3989
1069
3662
2946
-8918
5581
-632
77
478
372
-8108
5539
-5039
7625
-9984
8677
4351
-1384
8217
-4712
-7504
-5632
-6997
1074
917
-8910
3383
7446
9396
-2767
-1978
7989
-7629
-6774
-1238
4380
-5057
4663
2655
9276
-4615
-6572
6811
-316
-9522
-4319
7737
-507
1074
-522
-5519
-927
5257
6229
3036
-2886
3988
7890
-9667
1503
-1777
-7835
-4404
9109
1879
-3923
-8319
8399
706
1742
-1919
-4686
-2686
-9893
-4511
4458
8980
-6490
494
-3212
8139
-6941
-1188
-5911
2978
-9249
1550
-7080
-926
-6967
-1834
4295
-7059
-6075
-5694
1508
-711
-9560
-5262
-6409
1013
-8141
-1258
-5000
-5884
5272
4878
-9549
6590
-3347
4866
462
-4179
-234
8487
9330
-4082
4405
-2363
-4293
-3049
-6209
4096
-2193
-9033
3515
6971
3573
-2756
-7691
-3300
2832
6558
4092
-179
-6090
-6263
9184
-8756
-2178
-962
-3814
-1399
9650
-7306
-148
1589
4765
-5345
6592
6895
-316
6540
-7401
6286
-8719
632
-1249
7960
6140
238
-3238
6947
129
7407
-7969
6836
1864
9226
7373
1588
151
-7845
-678
3953
5125
1744
6640
4295
4993
8016
6039
2839
410
6843
-9936
-7388
-390
-3569
5284
5495
1879
-8246
-2245
-2373
-1709
-1955
-4681
587
-3120
-3947
5097
-9202
910
3171
4077
5246
8406
-340
3771
-7455
-3859
801
-1785
979
7722
-9880
-7717
-1842
6799
231
-2026
-6209
4185
-6875
-154
8406
2082
-3176
9886
2586
-5807
-6836
7583
-2526
4885
7135
3632
8756
-4510
-7436
-3324
-7540
-5525
-7383
-8651
9879
6153
403
6801
7283
1200
7899
2211
-7835
-9146
5251
-847
-3543
-6766
9999
7415
7074
307
9805
1209
-6275
-8749
-6766
-7933
6010
4473
793
-9292
4842
4570
-8348
-5618
-8115
5544
-4459
687
-2027
-2872
8346
1058
570
-5270
4805
8829
-8782
1602
5869
6882
-592
-9319
2362
4301
6136
6362
-3105
-7368
-8261
5362
6161
4136
-3256
3738
5656
-153
-2433
5630
-5366
-5885
-8215
9674
4524
4104
5822
-5219
-3563
1730
-2094
-3176
67
2005
-9388
5774
8872
4368
-4160
-5418
-2355
5015
7520
2857
8605
-5280
-8458
-5283
1363
-8265
5235
-3962
-5971
4732
5022
-4055
-7296
-3035
-1464
4661
5952
-3990
4534
-563
9104
5344
-1045
-4986
7336
1862
-7002
-2908
9523
244
1335
9671
1918
6073
2143
4804
7203
-7388
7419
-6980
-6677
-6174
7053
-502
-8348
-2549
4796
-9168
-5283
-5326
-4153
-1799
8692
-7567
1251
5847
4053
-771
-4789
7764
6160
543
9577
2177
6686
48
-4356
-2785
-7890
-6364
4696
-1681
-9837
-6078
6594
8770
3819
-969
-6422
4524
-5922
5190
-2174
-8056
-4568
-3449
8103
-4986
1051
2415
1209
-9719
6993
-6586
""".trimIndent()


/**

 */