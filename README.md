# sentiment
1. overvoew
This is a sentiment polarity classifier, which includes:
  ---- SVM based libsvm
  ---- Maximum Entropy
  ---- Naive Bayesian
  ---- Classifier only based on the Dictionary
  ---- K-NN

The sample is shown as a unit test!

If there is any problems, please contacts me, and I will revise it at once, and welcome anyone to refer to it.

Thanks!

2. How to use it.
you can refer to the unit test:kse.seu.edu.cn.SentimentPolarity.AppJunitTest.java.
first you should get a classifier instance,(new a instance), via the constructor function to set your training text file, 
every classifier has a "classify" function, you just input a String which you want to know the sentiment polarity.

Note: 
1. the training file: the first character of each line should be "pos" or "neg" to show the sentiment polarity.
2. the classifier main focus on the Chinese text.
3. The classifier based on the Dictionary require sentiment dictionary.
