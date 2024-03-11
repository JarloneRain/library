package com.demo.library.mapper;

import com.demo.library.Tool;
import com.demo.library.entity.Cord;
import com.demo.library.entity.Reco;
import com.demo.library.entity.Record;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RecordMapperTest {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    ToolMapper toolMapper;

    @Test //目测正常
    public void testGet() {
        System.out.println(recordMapper.getRecord(null));
    }

    @Test
    public void test() {

        Record[] records = new Record[50];
        List<Record> theRecords;
        List<Reco> theRecos;
        List<Cord> theCords;

        //先插入若干条reco
        for (int i = 5; i < 10; i++) {//user
            for (int j = 40; j < 50; j++) {//book
                int t = (i - 5) * 10 + j - 40;
                records[t] = new Record(null, new Reco(null, i, j), new ArrayList<>());
                recordMapper.insertReco(records[t].getReco());
            }
        }

        //取出来，检测一下reco是否正常，然后把reco的ID做了
        theRecords = recordMapper.getRecord(null).stream().sorted(Comparator.comparing(Record::getRecordId)).toList();
        theRecords = theRecords.subList(theRecords.size() - 50, theRecords.size());
        for (int t = 0; t < 50; t++) {
            Assert.assertEquals(records[t].getReco().getUserId(), theRecords.get(t).getReco().getUserId());
            Assert.assertEquals(records[t].getReco().getBookId(), theRecords.get(t).getReco().getBookId());
            records[t].setRecordId(theRecords.get(t).getRecordId());
        }

        //开始插入若干cords
        for (int i = 5; i < 10; i++) {//user
            for (int j = 40; j < 50; j++) {//book
                int t = (i - 5) * 10 + j - 40;
                while (Tool.randomBoolean()) {
                    Cord cord = new Cord(null, records[t].getReco().getId(), toolMapper.now(), Tool.randomCordType());
                    records[t].getCords().add(cord);
                    recordMapper.insertCord(cord);
                }
            }
        }

        //取出来对比
        theRecords = recordMapper.getRecord(null).stream().sorted(Comparator.comparing(Record::getRecordId)).toList();
        theRecords = theRecords.subList(theRecords.size() - 50, theRecords.size());
        //先排下序
        for (int i = 5; i < 10; i++) {//user
            for (int j = 40; j < 50; j++) {//book
                int t = (i - 5) * 10 + j - 40;
                theRecords.get(t).setCords(
                        theRecords.get(t).getCords().stream().sorted(Comparator.comparing(Cord::getId)).toList());
            }
        }
        //开始对照
        for (int i = 5; i < 10; i++) {//user
            for (int j = 40; j < 50; j++) {//book
                int t = (i - 5) * 10 + j - 40;
                for (int k = 0; k < records[t].getCords().size(); k++) {
                    Assert.assertEquals("i=" + i + ",j=" + j + ",k=" + k,
                            records[t].getCords().get(k).getTime(),
                            theRecords.get(t).getCords().get(k).getTime());
                    Assert.assertEquals("i=" + i + ",j=" + j + ",k=" + k,
                            records[t].getCords().get(k).getType(),
                            theRecords.get(t).getCords().get(k).getType());
                }
            }
        }

        //测试query by userId
        for (int i = 5; i < 10; i++) {//user
            Reco reco = new Reco(null, i, null);
            theRecords = recordMapper.query(reco).stream().sorted(Comparator.comparing(Record::getRecordId)).toList();
            //10 books
            theRecords = theRecords.subList(theRecords.size() - 10, theRecords.size());
            for (int j = 40; j < 50; j++) {//book
                int t = (i - 5) * 10 + j - 40;
                int tt = j - 40;
                theRecords.get(tt).setCords(
                        theRecords.get(tt).getCords().stream().sorted(Comparator.comparing(Cord::getId)).toList());

                Assert.assertEquals(records[t].getReco().getUserId(), theRecords.get(tt).getReco().getUserId());
                Assert.assertEquals(records[t].getReco().getBookId(), theRecords.get(tt).getReco().getBookId());
                for (int k = 0; k < records[k].getCords().size(); k++) {
                    Assert.assertEquals("i=" + i + ",j=" + j + ",k=" + k,
                            records[t].getCords().get(k).getTime(),
                            theRecords.get(tt).getCords().get(k).getTime());
                    Assert.assertEquals("i=" + i + ",j=" + j + ",k=" + k,
                            records[t].getCords().get(k).getType(),
                            theRecords.get(tt).getCords().get(k).getType());
                }
            }
        }
        //测试query by bookId
        for (int j = 40; j < 50; j++) {//book
            Reco reco = new Reco(null, null, j);
            theRecords = recordMapper.query(reco).stream().sorted(Comparator.comparing(Record::getRecordId)).toList();
            //5 users
            theRecords = theRecords.subList(theRecords.size() - 5, theRecords.size());
            for (int i = 5; i < 10; i++) {//book
                int t = (i - 5) * 10 + j - 40;
                int tt = i - 5;
                theRecords.get(tt).setCords(
                        theRecords.get(tt).getCords().stream().sorted(Comparator.comparing(Cord::getId)).toList());

                Assert.assertEquals(records[t].getReco().getUserId(), theRecords.get(tt).getReco().getUserId());
                Assert.assertEquals(records[t].getReco().getBookId(), theRecords.get(tt).getReco().getBookId());
                for (int k = 0; k < records[k].getCords().size(); k++) {
                    Assert.assertEquals("i=" + i + ",j=" + j + ",k=" + k,
                            records[t].getCords().get(k).getTime(),
                            theRecords.get(tt).getCords().get(k).getTime());
                    Assert.assertEquals("i=" + i + ",j=" + j + ",k=" + k,
                            records[t].getCords().get(k).getType(),
                            theRecords.get(tt).getCords().get(k).getType());
                }
            }
        }

    }
}
