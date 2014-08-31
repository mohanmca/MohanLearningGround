Traits

Reference :
1) How trait implemented in Scala - http://stackoverflow.com/questions/7637752/using-scala-traits-with-implemented-methods-in-java

2) Self type, The compiler will check that any class in a hierarchy including SpellChecker is or extends RandomAccessSeq[char], so SpellChecker can now use the fields or methods of RandomAccessSeq[char]. (Ref : http://markthomas.info/blog/?p=92)

    trait SpellChecker { self: RandomAccessSeq[char] =>
      ...
    }


