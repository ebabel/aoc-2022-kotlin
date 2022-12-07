package y2022

private val totalFileSystemSize = 70000000
private val neededForUpdate = 30000000

fun main(args: Array<String>) {

    println("Part1 test ${part1(testInput)}")
    println("Part1 real ${part1(input)}")
//    println("Part2 test ${part2(testInput)}")
//    println("Part2 real ${part2(input)}")
}

private data class File(val name: String, var size: Int?, val isDir: Boolean, val files: MutableSet<File>?, var parent: File?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as File

        if (name != other.name) return false
        if (parent != other.parent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (parent?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "File(name='$name', size=$size, isDir=$isDir parent=${parent?.name})"
    }
}


private fun part1(input: String): String {

    val root = File("/", null, true, mutableSetOf(), null)
    var workingFile = root

    input.lines().forEach {line ->
        val words = line.split(" ")
        if (words[0] =="dir") { // dir
            workingFile.files!!.add(File(words.last(), null,  true, mutableSetOf(), workingFile))
        } else if (words[0].toIntOrNull() != null) { // file
            workingFile.files!!.add(File(words.last(), words.first().toInt(), false, null, workingFile).also { println(it) })
        } else if (line == "$ cd /") { // back to root
            workingFile = root
        } else if (line.startsWith("$ cd ..")) { // into a dir
            workingFile = workingFile.parent!!
        } else if (line.startsWith("$ cd")) { // into a dir
            workingFile = workingFile.files!!.first { it.name == words.last() }
        } else {
            // ls
        }

    }

    val smallFiles = mutableListOf<File>()
    val dirSizeMap = mutableMapOf<File, Int>()
    fun populateDirSize(file: File) {
        println("populating $file ")
        file.files?.forEach {
            if (it.isDir) {
                populateDirSize(it)
            }
        }
        file.files?.map {
            it.size!!//.also { println("filesize ${file.name} ${file.size}") }
        }?.also {subFile ->
           file.size = subFile.sum().also {
               if (it < 100000) {
//                   println("${file.name} is only $it")
                   smallFiles.add(file)
               }
           }
            dirSizeMap[file] = file.size!!
//           println("${file.name} populated to ${file.size}}")

        }
    }

    populateDirSize(root)

    dirSizeMap.toList().sortedBy {
        it.second
    }.forEach { println(it) }

    val spaceUsed = 40208860
    // 70000000 -
    // 40208860 = 29,791,140
    // 30000000 - 29,791,140 -> 208,860

    smallFiles.sumOf { it.size!! }.also(::println)


    return "result"
}

private fun part2(input: String): String {

    return "result"
}


private val testInput =
    """
${'$'} cd /
${'$'} ls
dir a
14848514 b.txt
8504156 c.dat
dir d
${'$'} cd a
${'$'} ls
dir e
29116 f
2557 g
62596 h.lst
${'$'} cd e
${'$'} ls
584 i
${'$'} cd ..
${'$'} cd ..
${'$'} cd d
${'$'} ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
""".trimIndent()
private val input =
    """
${'$'} cd /
${'$'} ls
149291 cgc.vzv
dir cmcrzdt
dir hwdvrrp
26925 hwqvsl
dir lsmv
dir ngfllcq
dir ngnzzmpc
dir pwhjps
dir rgwnzttf
260556 tcglclw.hsn
dir trvznjhb
dir wgcqrc
68873 whpnhm
${'$'} cd cmcrzdt
${'$'} ls
dir chqllfw
95243 hjpf
108868 hwqvsl
115004 jpppczvz.mtp
dir lnsgfnbr
dir pdtjlb
dir rqfzvwts
dir trvznjhb
${'$'} cd chqllfw
${'$'} ls
56623 cgs.hbt
134804 zqb.grc
${'$'} cd ..
${'$'} cd lnsgfnbr
${'$'} ls
dir jtzw
dir ngfllcq
dir sdm
dir wlsg
${'$'} cd jtzw
${'$'} ls
dir nfz
${'$'} cd nfz
${'$'} ls
255427 hwqvsl
94147 tmnjbqq.fzh
${'$'} cd ..
${'$'} cd ..
${'$'} cd ngfllcq
${'$'} ls
110661 cdgqtwcv.lzn
208050 dpf
${'$'} cd ..
${'$'} cd sdm
${'$'} ls
dir dhwm
dir ngfllcq
125983 rfdz.vqm
24227 tzn
41909 tzn.vnr
dir zdzq
${'$'} cd dhwm
${'$'} ls
dir clr
dir lhv
dir ncvmgn
212499 ngfllcq.dcr
191108 nggnj
dir pdtjlb
dir pwhjps
dir sqqbthdp
dir trvznjhb
${'$'} cd clr
${'$'} ls
dir lnbc
87079 npwhncc
109530 pfqhpr.tzl
249566 tmnjbqq.fzh
dir zgmgztcd
${'$'} cd lnbc
${'$'} ls
62635 ftshngp.vbj
${'$'} cd ..
${'$'} cd zgmgztcd
${'$'} ls
149111 pwhjps.fjm
${'$'} cd ..
${'$'} cd ..
${'$'} cd lhv
${'$'} ls
dir phzfwl
${'$'} cd phzfwl
${'$'} ls
dir gmzcjzm
${'$'} cd gmzcjzm
${'$'} ls
263161 vsptqdv
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ncvmgn
${'$'} ls
245840 hjpf
21272 pbcjtbg
dir stm
${'$'} cd stm
${'$'} ls
dir hnbrd
${'$'} cd hnbrd
${'$'} ls
102906 lftjtq.gdt
45082 vsptqdv
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd pdtjlb
${'$'} ls
171382 hwqvsl
${'$'} cd ..
${'$'} cd pwhjps
${'$'} ls
75342 cgc.vzv
185458 hwqvsl
254359 ngfllcq.jzd
dir pdtjlb
200999 slnlws.sgh
91174 vqqbcb
dir zmc
${'$'} cd pdtjlb
${'$'} ls
39001 ngfllcq
${'$'} cd ..
${'$'} cd zmc
${'$'} ls
dir cjqmc
${'$'} cd cjqmc
${'$'} ls
257668 ctsfdprp
889 mzr.hnp
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd sqqbthdp
${'$'} ls
154217 pwhjps.jtn
${'$'} cd ..
${'$'} cd trvznjhb
${'$'} ls
105431 hwqvsl
${'$'} cd ..
${'$'} cd ..
${'$'} cd ngfllcq
${'$'} ls
183850 dcppzj.lmm
131039 hbpn.zlp
110398 hjpf
dir pwhjps
251784 rqgslj.sqg
dir szqf
150728 vsptqdv
${'$'} cd pwhjps
${'$'} ls
dir pzrtwv
156616 rpbh.ftj
dir tzn
${'$'} cd pzrtwv
${'$'} ls
197890 tzn
${'$'} cd ..
${'$'} cd tzn
${'$'} ls
121296 pdtjlb.nmg
${'$'} cd ..
${'$'} cd ..
${'$'} cd szqf
${'$'} ls
dir ngfllcq
dir qtrhn
${'$'} cd ngfllcq
${'$'} ls
dir vnfcczg
${'$'} cd vnfcczg
${'$'} ls
86691 cgc.vzv
189290 hjds.lwf
230265 hwqvsl
dir jbmvmzn
223129 ngfllcq.mdw
dir rpcbpjvm
215119 tmnjbqq.fzh
${'$'} cd jbmvmzn
${'$'} ls
dir flrszsrr
175047 hjpf
dir jrzf
dir ngfllcq
${'$'} cd flrszsrr
${'$'} ls
163007 zdvfmqr.pfq
${'$'} cd ..
${'$'} cd jrzf
${'$'} ls
32641 qbnz
${'$'} cd ..
${'$'} cd ngfllcq
${'$'} ls
dir dlcvcd
dir gcpftfm
183962 tzn.mjh
${'$'} cd dlcvcd
${'$'} ls
260612 mhf
${'$'} cd ..
${'$'} cd gcpftfm
${'$'} ls
227489 hwqvsl
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd rpcbpjvm
${'$'} ls
dir tnwzgrvw
${'$'} cd tnwzgrvw
${'$'} ls
dir trvznjhb
${'$'} cd trvznjhb
${'$'} ls
127767 pdtjlb.qjw
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd qtrhn
${'$'} ls
81716 dngdll
76367 tdj
180116 tmnjbqq.fzh
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd zdzq
${'$'} ls
192339 bqcmzm.vzw
dir cplvj
dir drpmlmf
92165 mcthl.dzw
dir qccnln
${'$'} cd cplvj
${'$'} ls
dir gmqddf
71720 hjpf
220700 hwqvsl
260353 lgw.msr
${'$'} cd gmqddf
${'$'} ls
36587 dmdgjrs
${'$'} cd ..
${'$'} cd ..
${'$'} cd drpmlmf
${'$'} ls
4896 hjpf
dir ngfllcq
65712 pwhjps.mng
67097 tmnjbqq.fzh
${'$'} cd ngfllcq
${'$'} ls
248967 swvf.prt
${'$'} cd ..
${'$'} cd ..
${'$'} cd qccnln
${'$'} ls
dir tzn
${'$'} cd tzn
${'$'} ls
81833 vsptqdv
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd wlsg
${'$'} ls
181214 nmglzcds.hcg
195698 pdtjlb.vbr
dir trvznjhb
77162 vsptqdv
${'$'} cd trvznjhb
${'$'} ls
237856 trvznjhb
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd pdtjlb
${'$'} ls
101237 hwqvsl
dir jssl
dir ngfllcq
dir pvlqvdrw
dir pwhjps
dir tzn
115255 vsptqdv
${'$'} cd jssl
${'$'} ls
76335 smmjjrp
${'$'} cd ..
${'$'} cd ngfllcq
${'$'} ls
102639 cgc.vzv
55243 fjfhdtr.ltc
${'$'} cd ..
${'$'} cd pvlqvdrw
${'$'} ls
38570 pwhjps.qgq
191322 scnbjgg.gww
${'$'} cd ..
${'$'} cd pwhjps
${'$'} ls
dir ghfwwj
dir vtr
${'$'} cd ghfwwj
${'$'} ls
109461 mdtp.ztw
${'$'} cd ..
${'$'} cd vtr
${'$'} ls
dir fmtpdc
${'$'} cd fmtpdc
${'$'} ls
42101 bcdbqcs.lhp
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd tzn
${'$'} ls
dir pdtjlb
${'$'} cd pdtjlb
${'$'} ls
154813 vsptqdv
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd rqfzvwts
${'$'} ls
250112 hwqvsl
63241 tzn
149460 tzn.pph
${'$'} cd ..
${'$'} cd trvznjhb
${'$'} ls
784 bdptcbl.ntt
108339 cgc.vzv
dir hnpdrdsm
dir mnnwcmd
dir ngfllcq
dir pbsnd
dir pdtjlb
261083 rhl.cjh
dir trvznjhb
103421 wjftfs
${'$'} cd hnpdrdsm
${'$'} ls
253895 pwhjps.nps
36928 ssjhl
235679 tmnjbqq.fzh
38049 trvznjhb.dzs
${'$'} cd ..
${'$'} cd mnnwcmd
${'$'} ls
218411 fvzhntq.rwm
78694 gwlcbbtm
243270 hjpf
15610 trvznjhb.wdj
${'$'} cd ..
${'$'} cd ngfllcq
${'$'} ls
dir fmhlq
dir fwbdttbj
dir hccstwh
${'$'} cd fmhlq
${'$'} ls
142240 rbrwv.hjl
dir tjpwvb
256604 tmnjbqq.fzh
dir trvznjhb
${'$'} cd tjpwvb
${'$'} ls
83436 sfrpt
${'$'} cd ..
${'$'} cd trvznjhb
${'$'} ls
44433 trvznjhb
${'$'} cd ..
${'$'} cd ..
${'$'} cd fwbdttbj
${'$'} ls
4127 hwqvsl
${'$'} cd ..
${'$'} cd hccstwh
${'$'} ls
dir cbd
243765 lvzsrqlw.llc
${'$'} cd cbd
${'$'} ls
158372 vzgtjqbd.tmd
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd pbsnd
${'$'} ls
172548 cgc.vzv
261836 pwhjps
${'$'} cd ..
${'$'} cd pdtjlb
${'$'} ls
73184 cgc.vzv
dir mdbjwvh
dir mgt
dir nbrvvghc
98702 ngf.gtb
dir ngfllcq
224788 pdtjlb
191754 tmnjbqq.fzh
189444 tnqwbmzm.vlq
dir tzn
dir ztzsg
${'$'} cd mdbjwvh
${'$'} ls
135602 nvt.rjh
${'$'} cd ..
${'$'} cd mgt
${'$'} ls
dir pbrtf
dir whflvwv
${'$'} cd pbrtf
${'$'} ls
dir tnnnllg
${'$'} cd tnnnllg
${'$'} ls
269436 hjpf
${'$'} cd ..
${'$'} cd ..
${'$'} cd whflvwv
${'$'} ls
185562 ngfllcq
dir rdl
150984 trvznjhb
${'$'} cd rdl
${'$'} ls
231952 mqjcttf
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd nbrvvghc
${'$'} ls
dir pdtjlb
${'$'} cd pdtjlb
${'$'} ls
40347 hjpf
dir vwl
${'$'} cd vwl
${'$'} ls
124408 tzn.hjw
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ngfllcq
${'$'} ls
54894 bvgf
${'$'} cd ..
${'$'} cd tzn
${'$'} ls
41506 vnhlvqqw.cvd
${'$'} cd ..
${'$'} cd ztzsg
${'$'} ls
216433 fzsnpr.vrd
dir grrngq
dir hcmvbhp
dir lbmnq
19985 ngfllcq
dir pqqjgbvj
dir zsnggz
${'$'} cd grrngq
${'$'} ls
dir cqcvb
dir ngfllcq
dir shrhb
${'$'} cd cqcvb
${'$'} ls
125712 cgc.vzv
69800 fpszwd
160009 rbbwsszz
dir trvznjhb
33640 tzghd.fjp
${'$'} cd trvznjhb
${'$'} ls
dir gtjfll
dir mrncfvnp
dir pwt
dir trvznjhb
173974 tzn
${'$'} cd gtjfll
${'$'} ls
126840 cgc.vzv
180163 fswwvhwn
dir gnpcbvmt
122192 ngfllcq.snb
${'$'} cd gnpcbvmt
${'$'} ls
127188 crhvwb.pct
218972 mwg
${'$'} cd ..
${'$'} cd ..
${'$'} cd mrncfvnp
${'$'} ls
216569 phlbdl
${'$'} cd ..
${'$'} cd pwt
${'$'} ls
119692 tmnjbqq.fzh
${'$'} cd ..
${'$'} cd trvznjhb
${'$'} ls
203464 nltqsvd.fhc
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ngfllcq
${'$'} ls
31226 rdwczp.hfq
${'$'} cd ..
${'$'} cd shrhb
${'$'} ls
246035 bnbg
98513 hjpf
${'$'} cd ..
${'$'} cd ..
${'$'} cd hcmvbhp
${'$'} ls
dir cbb
201230 cgc.vzv
dir grmmpz
220707 hjpf
dir jnr
dir psv
dir trvznjhb
134184 trvznjhb.ghp
228507 vsptqdv
${'$'} cd cbb
${'$'} ls
17897 hwqvsl
${'$'} cd ..
${'$'} cd grmmpz
${'$'} ls
214171 spsnch.drs
${'$'} cd ..
${'$'} cd jnr
${'$'} ls
82130 bhjqplbc.rth
${'$'} cd ..
${'$'} cd psv
${'$'} ls
215898 pwhjps
${'$'} cd ..
${'$'} cd trvznjhb
${'$'} ls
dir dfcn
98111 gwt.fmw
20948 hjpf
dir pwhjps
dir rnlrgd
${'$'} cd dfcn
${'$'} ls
dir fqm
179114 mbcrjb
dir trvznjhb
dir vfrrzdb
${'$'} cd fqm
${'$'} ls
dir frgwsrdh
${'$'} cd frgwsrdh
${'$'} ls
142027 hhpwsl
${'$'} cd ..
${'$'} cd ..
${'$'} cd trvznjhb
${'$'} ls
dir nbbb
34253 ngfllcq.src
dir qgqmmvg
dir tfgwmlc
11919 trvznjhb.qgz
dir tzn
24383 zvfzhb.dcw
${'$'} cd nbbb
${'$'} ls
260947 pwhjps.bdq
${'$'} cd ..
${'$'} cd qgqmmvg
${'$'} ls
67028 wjvhq.tdz
${'$'} cd ..
${'$'} cd tfgwmlc
${'$'} ls
dir rmcgqpb
dir wdtmdtz
${'$'} cd rmcgqpb
${'$'} ls
263786 nsmcndc.bjs
${'$'} cd ..
${'$'} cd wdtmdtz
${'$'} ls
37751 lnspfqv.tpp
${'$'} cd ..
${'$'} cd ..
${'$'} cd tzn
${'$'} ls
265035 nqsgm.dhm
${'$'} cd ..
${'$'} cd ..
${'$'} cd vfrrzdb
${'$'} ls
dir pzbtsnd
dir srpdb
${'$'} cd pzbtsnd
${'$'} ls
72770 pdtjlb
${'$'} cd ..
${'$'} cd srpdb
${'$'} ls
231540 dzgsf
dir ngfllcq
dir sjv
${'$'} cd ngfllcq
${'$'} ls
26488 cgc.vzv
195815 dfjss
119177 lbjtjqr
${'$'} cd ..
${'$'} cd sjv
${'$'} ls
225677 msgjj
228113 ngfllcq
92448 tzn.rbp
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd pwhjps
${'$'} ls
171613 nzqd.rzh
${'$'} cd ..
${'$'} cd rnlrgd
${'$'} ls
132964 hjpf
146636 hwqvsl
187596 mlrllbbb.wmh
92535 trvznjhb
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd lbmnq
${'$'} ls
142963 qfpjgvll.ncb
${'$'} cd ..
${'$'} cd pqqjgbvj
${'$'} ls
dir dfhhzwp
253570 jjbr.cgf
dir lchljrwb
dir pdtjlb
${'$'} cd dfhhzwp
${'$'} ls
dir bqp
${'$'} cd bqp
${'$'} ls
122939 hjpf
${'$'} cd ..
${'$'} cd ..
${'$'} cd lchljrwb
${'$'} ls
259475 fqmppdtd.tjm
${'$'} cd ..
${'$'} cd pdtjlb
${'$'} ls
199658 vsptqdv
${'$'} cd ..
${'$'} cd ..
${'$'} cd zsnggz
${'$'} ls
117242 hjpf
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd trvznjhb
${'$'} ls
dir bgvqct
160709 dtq
dir fldlwj
dir ndq
221408 tmnjbqq.fzh
69148 zvfzt.rjm
${'$'} cd bgvqct
${'$'} ls
66024 cmjrmfn.lpt
40153 fdlvqgn.dbt
135848 tmnjbqq.fzh
${'$'} cd ..
${'$'} cd fldlwj
${'$'} ls
172275 ngfllcq.gbb
${'$'} cd ..
${'$'} cd ndq
${'$'} ls
117311 bbhlcn.qll
dir dtzmzgw
123263 hsb
dir jnthg
111208 pdtjlb
200860 pjq
${'$'} cd dtzmzgw
${'$'} ls
dir tzn
${'$'} cd tzn
${'$'} ls
249561 pszf.zcn
${'$'} cd ..
${'$'} cd ..
${'$'} cd jnthg
${'$'} ls
17013 pwhjps.gpp
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd hwdvrrp
${'$'} ls
dir fgvqft
dir fwc
dir sfhsb
dir tzn
dir wbtf
${'$'} cd fgvqft
${'$'} ls
215710 wzh
${'$'} cd ..
${'$'} cd fwc
${'$'} ls
184576 dmvqc.tsr
dir hgznwf
dir lfjtqz
dir ngfllcq
53748 ngfllcq.vgl
dir pgpvcp
${'$'} cd hgznwf
${'$'} ls
51012 tmnjbqq.fzh
${'$'} cd ..
${'$'} cd lfjtqz
${'$'} ls
96666 fwttv.qrp
203264 nhc.lgd
dir pwhjps
213570 tzn
${'$'} cd pwhjps
${'$'} ls
69941 frqq.jnv
136814 pqmsc.dgz
185821 rww.trv
${'$'} cd ..
${'$'} cd ..
${'$'} cd ngfllcq
${'$'} ls
97361 zcw.zrq
${'$'} cd ..
${'$'} cd pgpvcp
${'$'} ls
834 nwv.mtw
${'$'} cd ..
${'$'} cd ..
${'$'} cd sfhsb
${'$'} ls
78513 pdtjlb
${'$'} cd ..
${'$'} cd tzn
${'$'} ls
dir lpf
${'$'} cd lpf
${'$'} ls
242317 bngfvvgq.ptp
82304 ngfllcq.qdz
dir wsvqtcb
${'$'} cd wsvqtcb
${'$'} ls
32176 vrwlnphn.nnv
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd wbtf
${'$'} ls
53446 jvvdpn
41661 ngfllcq.vhl
dir pwhjps
231151 tzn
241080 vdzdhdtb.dgj
dir vlqmz
${'$'} cd pwhjps
${'$'} ls
200296 hdds.lsw
${'$'} cd ..
${'$'} cd vlqmz
${'$'} ls
166538 pwhjps.mnq
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd lsmv
${'$'} ls
dir dtjjv
87897 hjpf
216417 hwqvsl
dir ngfllcq
dir pdtjlb
dir qlnlbcdv
230724 vsptqdv
177119 vvzvnn
${'$'} cd dtjjv
${'$'} ls
218742 hjpf
${'$'} cd ..
${'$'} cd ngfllcq
${'$'} ls
38560 cgc.vzv
257037 fbttg.jlc
29948 pwhjps.bvj
1253 trvznjhb.nzl
241388 tzn.vdb
dir wlmtj
${'$'} cd wlmtj
${'$'} ls
51957 hjpf
27480 pwhjps.hgj
dir qdjfgz
dir shb
182077 tclmtwh.wzr
dir trvznjhb
103119 twlf.rnl
31950 tzn.zfm
${'$'} cd qdjfgz
${'$'} ls
238882 hpms.gll
dir qpbsmmp
184633 trvznjhb.nsb
130374 vsptqdv
${'$'} cd qpbsmmp
${'$'} ls
60269 spsbz
${'$'} cd ..
${'$'} cd ..
${'$'} cd shb
${'$'} ls
140111 vsptqdv
${'$'} cd ..
${'$'} cd trvznjhb
${'$'} ls
dir qjqzppj
${'$'} cd qjqzppj
${'$'} ls
203246 hjpf
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd pdtjlb
${'$'} ls
41982 hjpf
245930 hwqvsl
dir mmnhtmr
42314 ngfllcq.tcn
68269 pdtjlb
103066 vhtjp.grt
${'$'} cd mmnhtmr
${'$'} ls
dir zqjjgvj
${'$'} cd zqjjgvj
${'$'} ls
263209 nvhflpng.ngd
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd qlnlbcdv
${'$'} ls
dir jmd
58921 pdtjlb.mwb
dir pzjgmm
dir qqvrvcw
79958 rrqmn.zwv
18158 swjpt.trv
dir trvznjhb
dir tzn
92135 zjb.nns
268795 zspzsb.szp
${'$'} cd jmd
${'$'} ls
137766 pwhjps
${'$'} cd ..
${'$'} cd pzjgmm
${'$'} ls
1704 tzn.rhf
66307 tzn.zll
116623 vrfvctv.clb
${'$'} cd ..
${'$'} cd qqvrvcw
${'$'} ls
179302 zrqf.fcn
${'$'} cd ..
${'$'} cd trvznjhb
${'$'} ls
265026 qfzlgccf.hvz
dir rbbmmcc
${'$'} cd rbbmmcc
${'$'} ls
dir rtr
${'$'} cd rtr
${'$'} ls
dir dtw
${'$'} cd dtw
${'$'} ls
249472 svs.tgj
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd tzn
${'$'} ls
80112 pdtjlb.thm
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ngfllcq
${'$'} ls
228868 ggcfwgr.mwh
10205 gztwg.pwz
136188 hjpf
141381 hwqvsl
250522 pdtjlb.dwg
dir pwhjps
dir qcwvfl
dir tzn
dir zgwcwqr
${'$'} cd pwhjps
${'$'} ls
19881 tmrljtw
${'$'} cd ..
${'$'} cd qcwvfl
${'$'} ls
63317 fcjsw.jcj
dir gvvfsq
272464 lvqc
148216 nwppjnwc.sdg
121107 tzn.ppw
dir vwfb
${'$'} cd gvvfsq
${'$'} ls
80607 jplds.mjz
${'$'} cd ..
${'$'} cd vwfb
${'$'} ls
dir gtlfdvjz
${'$'} cd gtlfdvjz
${'$'} ls
228623 jbbplpz
dir shf
${'$'} cd shf
${'$'} ls
120966 cgc.vzv
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd tzn
${'$'} ls
215528 cgc.vzv
112331 gtzcl.rzp
128653 mqd.dcz
dir ngfllcq
dir vmfgbzmw
${'$'} cd ngfllcq
${'$'} ls
207193 qchb.hmv
${'$'} cd ..
${'$'} cd vmfgbzmw
${'$'} ls
16152 vtlgffn
${'$'} cd ..
${'$'} cd ..
${'$'} cd zgwcwqr
${'$'} ls
dir pdtjlb
110033 vsptqdv
${'$'} cd pdtjlb
${'$'} ls
9746 cgc.vzv
8010 jdvjpps
${'$'} cd ..
${'$'} cd ..
${'$'} cd ..
${'$'} cd ngnzzmpc
${'$'} ls
116647 gmsnm
157873 hwqvsl
${'$'} cd ..
${'$'} cd pwhjps
${'$'} ls
96321 cgc.vzv
dir lcds
dir tzn
${'$'} cd lcds
${'$'} ls
134975 wcfv.gpd
${'$'} cd ..
${'$'} cd tzn
${'$'} ls
95149 hjpf
55950 pwhjps.rpq
166540 tdt.pgw
236704 trvznjhb.ccn
${'$'} cd ..
${'$'} cd ..
${'$'} cd rgwnzttf
${'$'} ls
122721 hjpf
${'$'} cd ..
${'$'} cd trvznjhb
${'$'} ls
106424 zvqz
${'$'} cd ..
${'$'} cd wgcqrc
${'$'} ls
87367 hjpf
63133 lld
234148 pwhjps.lcr
dir rjnnz
19538 tzn
233765 zlvznnwj
${'$'} cd rjnnz
${'$'} ls
258856 gpgdm
""".trimIndent()


/**
 * --- Day 7: No Space Left On Device ---
You can hear birds chirping and raindrops hitting leaves as the expedition proceeds. Occasionally, you can even hear much louder sounds in the distance; how big do the animals get out here, anyway?

The device the Elves gave you has problems with more than just its communication system. You try to run a system update:

$ system-update --please --pretty-please-with-sugar-on-top
Error: No space left on device
Perhaps you can delete some files to make space for the update?

You browse around the filesystem to assess the situation and save the resulting terminal output (your puzzle input). For example:

$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
The filesystem consists of a tree of files (plain data) and directories (which can contain other directories or files). The outermost directory is called /. You can navigate around the filesystem, moving into or out of directories and listing the contents of the directory you're currently in.

Within the terminal output, lines that begin with $ are commands you executed, very much like some modern computers:

cd means change directory. This changes which directory is the current directory, but the specific result depends on the argument:
cd x moves in one level: it looks in the current directory for the directory named x and makes it the current directory.
cd .. moves out one level: it finds the directory that contains the current directory, then makes that directory the current directory.
cd / switches the current directory to the outermost directory, /.
ls means list. It prints out all of the files and directories immediately contained by the current directory:
123 abc means that the current directory contains a file named abc with size 123.
dir xyz means that the current directory contains a directory named xyz.
Given the commands and output in the example above, you can determine that the filesystem looks visually like this:

- / (dir)
- a (dir)
- e (dir)
- i (file, size=584)
- f (file, size=29116)
- g (file, size=2557)
- h.lst (file, size=62596)
- b.txt (file, size=14848514)
- c.dat (file, size=8504156)
- d (dir)
- j (file, size=4060174)
- d.log (file, size=8033020)
- d.ext (file, size=5626152)
- k (file, size=7214296)
Here, there are four directories: / (the outermost directory), a and d (which are in /), and e (which is in a). These directories also contain files of various sizes.

Since the disk is full, your first step should probably be to find directories that are good candidates for deletion. To do this, you need to determine the total size of each directory. The total size of a directory is the sum of the sizes of the files it contains, directly or indirectly. (Directories themselves do not count as having any intrinsic size.)

The total sizes of the directories above can be found as follows:

The total size of directory e is 584 because it contains a single file i of size 584 and no other directories.
The directory a has total size 94853 because it contains files f (size 29116), g (size 2557), and h.lst (size 62596), plus file i indirectly (a contains e which contains i).
Directory d has total size 24933642.
As the outermost directory, / contains every file. Its total size is 48381165, the sum of the size of every file.
To begin, find all of the directories with a total size of at most 100000, then calculate the sum of their total sizes. In the example above, these directories are a and e; the sum of their total sizes is 95437 (94853 + 584). (As in this example, this process can count files more than once!)

Find all of the directories with a total size of at most 100000. What is the sum of the total sizes of those directories?

Your puzzle answer was 1583951.

--- Part Two ---
Now, you're ready to choose a directory to delete.

The total disk space available to the filesystem is 70000000. To run the update, you need unused space of at least 30000000. You need to find a directory you can delete that will free up enough space to run the update.

In the example above, the total size of the outermost directory (and thus the total amount of used space) is 48381165; this means that the size of the unused space must currently be 21618835, which isn't quite the 30000000 required by the update. Therefore, the update still requires a directory with total size of at least 8381165 to be deleted before it can run.

To achieve this, you have the following options:

Delete directory e, which would increase unused space by 584.
Delete directory a, which would increase unused space by 94853.
Delete directory d, which would increase unused space by 24933642.
Delete directory /, which would increase unused space by 48381165.
Directories e and a are both too small; deleting them would not free up enough space. However, directories d and / are both big enough! Between these, choose the smallest: d, increasing unused space by 24933642.

Find the smallest directory that, if deleted, would free up enough space on the filesystem to run the update. What is the total size of that directory?

Your puzzle answer was 214171.

Both parts of this puzzle are complete! They provide two gold stars: **
 */