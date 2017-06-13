package kse.seu.edu.cn.classifier;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huaban.analysis.jieba.JiebaSegmenter;

import kse.seu.edu.cn.tools.FileUtils;

/**
 * Created by LiuFeng on 2017/6/6.
 */
public class DictClassifier implements IClassify, Serializable {

	private static final long serialVersionUID = 1L;
	private List<Phrase> phrase_dict = null;
	private Map<String, Double> positive_dict = null;
	private Map<String, Double> negative_dict = null;
	private Map<String, Double> conjunction_dict = null;
	private Map<String, Double> punctuation_dict = null;
	private Map<String, Double> adverb_dict = null;
	private Map<String, Double> denial_dict = null;

	private String rootFilePath = "";
	private String phraseFilePath = "";
	private String positiveFilePath = "";
	private String negativeFilePath = "";
	private String conjunctionFilePath = "";
	private String punctuationFilePath = "";
	private String adverbFilePath = "";
	private String denialFilePath = "";

	public DictClassifier() {
		super();
	}

	public DictClassifier(String rootFilePath, String phraseFilePath, String positiveFilePath, String negativeFilePath,
			String conjunctionFilePath, String punctuationFilePath, String adverbFilePath, String denialFilePath) {
		super();
		this.rootFilePath = rootFilePath;
		this.phraseFilePath = phraseFilePath;
		this.phrase_dict = get_phrase_dict(phraseFilePath);
		this.positiveFilePath = positiveFilePath;
		this.positive_dict = get_positive_dict(positiveFilePath);
		this.negativeFilePath = negativeFilePath;
		this.negative_dict = get_negative_dict(negativeFilePath);
		this.conjunctionFilePath = conjunctionFilePath;
		this.conjunction_dict = get_conjunction_dict(conjunctionFilePath);
		this.punctuationFilePath = punctuationFilePath;
		this.punctuation_dict = get_punctuation_dict(punctuationFilePath);
		this.adverbFilePath = adverbFilePath;
		this.adverb_dict = get_adverb_dict(adverbFilePath);
		this.denialFilePath = denialFilePath;
		this.denial_dict = get_denialFilePath(denialFilePath);
	}

	private Map<String, Double> get_denialFilePath(String denialFilePath) {
		return FileUtils.file2Dic(rootFilePath + denialFilePath);
	}

	private Map<String, Double> get_adverb_dict(String adverbFilePath) {
		return FileUtils.file2Dic(rootFilePath + adverbFilePath);
	}

	private Map<String, Double> get_punctuation_dict(String punctuationFilePath) {
		return FileUtils.file2Dic(rootFilePath + punctuationFilePath);
	}

	private Map<String, Double> get_conjunction_dict(String conjunctionFilePath) {
		return FileUtils.file2Dic(rootFilePath + conjunctionFilePath);
	}

	private Map<String, Double> get_negative_dict(String negativeFilePath) {
		return FileUtils.file2Dic(rootFilePath + negativeFilePath);
	}

	private Map<String, Double> get_positive_dict(String positiveFilePath) {
		return FileUtils.file2Dic(rootFilePath + positiveFilePath);
	}

	private List<Phrase> get_phrase_dict(String phraseFilePath) {
		return FileUtils.parsePhraseDic(rootFilePath + phraseFilePath);
	}

	public List<Phrase> getPhrase_dict() {
		return phrase_dict;
	}

	public void setPhrase_dict(List<Phrase> phrase_dict) {
		this.phrase_dict = phrase_dict;
	}

	public Map<String, Double> getPositive_dict() {
		return positive_dict;
	}

	public void setPositive_dict(Map<String, Double> positive_dict) {
		this.positive_dict = positive_dict;
	}

	public Map<String, Double> getNegative_dict() {
		return negative_dict;
	}

	public void setNegative_dict(Map<String, Double> negative_dict) {
		this.negative_dict = negative_dict;
	}

	public Map<String, Double> getConjunction_dict() {
		return conjunction_dict;
	}

	public void setConjunction_dict(Map<String, Double> conjunction_dict) {
		this.conjunction_dict = conjunction_dict;
	}

	public Map<String, Double> getPunctuation_dict() {
		return punctuation_dict;
	}

	public void setPunctuation_dict(Map<String, Double> punctuation_dict) {
		this.punctuation_dict = punctuation_dict;
	}

	public Map<String, Double> getAdverb_dict() {
		return adverb_dict;
	}

	public void setAdverb_dict(Map<String, Double> adverb_dict) {
		this.adverb_dict = adverb_dict;
	}

	public Map<String, Double> getDenial_dict() {
		return denial_dict;
	}

	public void setDenial_dict(Map<String, Double> denial_dict) {
		this.denial_dict = denial_dict;
	}

	public String getRootFilePath() {
		return rootFilePath;
	}

	public void setRootFilePath(String rootFilePath) {
		this.rootFilePath = rootFilePath;
	}

	public String getPhraseFilePath() {
		return phraseFilePath;
	}

	public void setPhraseFilePath(String phraseFilePath) {
		this.phraseFilePath = phraseFilePath;
	}

	public String getPositiveFilePath() {
		return positiveFilePath;
	}

	public void setPositiveFilePath(String positiveFilePath) {
		this.positiveFilePath = positiveFilePath;
	}

	public String getNegativeFilePath() {
		return negativeFilePath;
	}

	public void setNegativeFilePath(String negativeFilePath) {
		this.negativeFilePath = negativeFilePath;
	}

	public String getConjunctionFilePath() {
		return conjunctionFilePath;
	}

	public void setConjunctionFilePath(String conjunctionFilePath) {
		this.conjunctionFilePath = conjunctionFilePath;
	}

	public String getPunctuationFilePath() {
		return punctuationFilePath;
	}

	public void setPunctuationFilePath(String punctuationFilePath) {
		this.punctuationFilePath = punctuationFilePath;
	}

	public String getAdverbFilePath() {
		return adverbFilePath;
	}

	public void setAdverbFilePath(String adverbFilePath) {
		this.adverbFilePath = adverbFilePath;
	}

	public String getDenialFilePath() {
		return denialFilePath;
	}

	public void setDenialFilePath(String denialFilePath) {
		this.denialFilePath = denialFilePath;
	}

	@Override
	public String classify(String sentence) {
		CommentAnalysis comment = new CommentAnalysis();
		return analyse_sentence(sentence, "results/output.txt", true, comment);
	}

	@Override
	public String classify(File file) {
		return analyse_File(file);
	}

	public String analyse_File(File file) {
		return null;
	}

	public String analyse_sentence(String sentence, String runoutFilePath, boolean print_show,
			CommentAnalysis comment) {
		List<String> clauses = divideSentenceIntoClauses(sentence + "%");
		for (String clause : clauses) {
			Subclause subclause = analyseClause(clause.replace("。", "."), runoutFilePath, print_show);
			comment.getThe_clauses().add(subclause);
			System.out.println(subclause.getScore());
			comment.setScore(comment.getScore() + subclause.getScore());
		}
		System.out.println("Total:"+comment.getScore());
		return comment.getScore()>0?"1":"0";
	}

	private Subclause analyseClause(String clause, String runoutFilePath, boolean print_show) {
		Subclause sub_clause = new Subclause();
		List<String> segResults = new JiebaSegmenter().sentenceProcess(clause);
		// 将分句及分词结果写进运行输出文件，以便复查
		FileUtils.writeString2File(runoutFilePath, clause);
		FileUtils.writeSList2File(runoutFilePath, segResults);
		if (print_show) {
			System.out.println(clause + ":" + segResults);
		}

		Orientation judgement = null;
		// 判断句式：如果……就好了
		judgement = isClausePattern2(clause);
		if (judgement != null) {
			sub_clause.getPatterns().add(judgement);
			sub_clause.setScore(sub_clause.getScore() - judgement.getValue());
			return sub_clause;
		}

		// 判断句式：是…不是…
		judgement = isClausePattern1(clause);
		if (judgement != null) {
			sub_clause.getPatterns().add(judgement);
			sub_clause.setScore(sub_clause.getScore() - judgement.getValue());
		}

		// 判断句式：短语
		judgement = isClausePattern3(clause, segResults);
		if (judgement != null) {
			sub_clause.setScore(sub_clause.getScore() + judgement.getValue());
			if (judgement.getValue() >= 0) {
				sub_clause.getPositives().add(judgement);
			} else if (judgement.getValue() < 0) {
				sub_clause.getNegatives().add(judgement);
			}

			String[] fields = judgement.getKey().split(":");
			String match_result = fields[fields.length - 1];

			int i = 0;
			while (i < segResults.size()) {
				if (match_result.contains(segResults.get(i))) {
					if ((i == segResults.size()) || (match_result.contains(segResults.get(i + 1)))) {
						segResults.remove(i);
						continue;
					}
				}
				i++;
			}
		}
		// 逐个分析分词
		for (int i = 0; i < segResults.size(); i++) {
			Dictionary result = analyse_word(segResults.get(i), segResults, i);
			if(result==null){
				continue;
			}
			Orientation element = new Orientation(result.getKey(), result.getValue());
			
			switch (result.getPosition()) {
			case 1:
				sub_clause.getConjunctions().add(element);
				break;
			case 2:
				sub_clause.getPunctuations().add(element);
				break;
			case 3:
				sub_clause.getPositives().add(element);
				sub_clause.setScore(sub_clause.getScore() + result.getValue());
				break;
			case 4:
				sub_clause.getNegatives().add(element);
				sub_clause.setScore(sub_clause.getScore() - result.getValue());

				break;
			default:
				break;
			}

			for (Orientation conjunction : sub_clause.getConjunctions()) {
				sub_clause.setScore(sub_clause.getScore() * conjunction.getValue());
			}
			for (Orientation punctuation : sub_clause.getPunctuations()) {
				sub_clause.setScore(sub_clause.getScore() * punctuation.getValue());
			}
		}
		return sub_clause;
	}

	private Dictionary analyse_word(String word, List<String> segResults, int index) {
		Dictionary judgement = null;

		// 判断是否是连词
		judgement = isWordConjunction(word);
		if (judgement != null) {
			return judgement;
		}
		// 判断是否是标点符号
		judgement = isWordPunctuation(word);
		if (judgement != null) {
			return judgement;
		}
		// 判断是否是正向情感词
		judgement = isWordPositive(word, segResults, index);
		if (judgement != null) {
			return judgement;
		}
		// 判断是否是负向情感词
		judgement = isWordNegative(word, segResults, index);
		if (judgement != null) {
			return judgement;
		}
		return null;
	}

	private Dictionary isWordNegative(String word, List<String> segResults, int index) {
		// 判断分词是否在情感词典内
		Dictionary judgement = null;

		if (negative_dict.containsKey(word)) {
			// 在情感词典内，则构建一个以情感词为中心的字典数据结构
			Orientation orient = emotional_word_analysis(word, negative_dict.get(word) + "", segResults, index);
			judgement = new Dictionary(orient.getKey(), orient.getValue(), 4);
		}
		return judgement;
	}

	private Dictionary isWordPositive(String word, List<String> segResults, int index) {

		// 判断分词是否在情感词典内
		Dictionary judgement = null;
		if (positive_dict.containsKey(word)) {
			// 在情感词典内，则构建一个以情感词为中心的字典数据结构
			Orientation orient = emotional_word_analysis(word, positive_dict.get(word) + "", segResults, index);
			judgement = new Dictionary(orient.getKey(), orient.getValue(), 3);
		}
		return judgement;
	}

	private Dictionary isWordPunctuation(String word) {
		Dictionary judgement = null;
		if (punctuation_dict.containsKey(word)) {
			judgement = new Dictionary(word, punctuation_dict.get(word), 2);
		}
		return judgement;
	}

	private Dictionary isWordConjunction(String word) {
		Dictionary judgement = null;
		if (conjunction_dict.containsKey(word)) {
			judgement = new Dictionary(word, conjunction_dict.get(word), 1);
		}
		return judgement;
	}

	private Orientation isClausePattern3(String clause, List<String> sunclauses) {

		for (Phrase phrase : phrase_dict) {
			String to_compile = phrase.getKey().replace("……", "[\u4e00-\u9fa5]*");
			if (!phrase.getStart().isEmpty())
				to_compile = to_compile.replace("*", "{" + phrase.getStart() + "," + phrase.getStart() + "}");
			if (!phrase.getHead().isEmpty())
				to_compile = phrase.getHead() + to_compile;

			Pattern pattern = Pattern.compile(to_compile);
			Matcher matcher = pattern.matcher(clause);
			if (matcher.find()) {
				boolean canContinue = true;
				List<String> pos = new JiebaSegmenter().sentenceProcess(matcher.group());

				if (!phrase.getBetween_tag().isEmpty()) {
					if (pos.size() > 2) {
						canContinue = false;
						for (String p : pos) {
							if (p.equals(phrase.getBetween_tag()))
								canContinue = true;
						}

					}
				}

				if (canContinue) {
					for (int i = 0; i < sunclauses.size(); i++) {
						if (matcher.group().contains(sunclauses.get(i))) {
							try {
								if (matcher.group().contains(sunclauses.get(i + 1))) {
									return emotional_word_analysis(phrase.getKey() + ":" + matcher.group(),
											phrase.getValue(), sunclauses, i);
								}
							} catch (Exception e) {
								return emotional_word_analysis(phrase.getKey() + ":" + matcher.group(),
										phrase.getValue(), sunclauses, i);
							}

						}
					}
				}

			}

		}
		return null;
	}

	private Orientation emotional_word_analysis(String core_word, String value, List<String> sunclauses, int index) {
		// 在情感词典内，则构建一个以情感词为中心的字典数据结构
		Orientation orient = new Orientation(core_word, Double.parseDouble(value));

		// 在三个前视窗内，判断是否有否定词、副词
		// 第一个视窗
		int viewWindow = index - 1;
		// 无越界
		if (viewWindow > -1) {
			// 判断前一个词是否是情感词
			if (negative_dict.containsKey(sunclauses.get(viewWindow))
					|| negative_dict.containsKey(sunclauses.get(viewWindow))) {
				return orient;
			}
			// 判断前一个词是否是副词
			if (adverb_dict.containsKey(sunclauses.get(viewWindow))) {
				Dictionary adverb = new Dictionary(sunclauses.get(viewWindow),
						adverb_dict.get(sunclauses.get(viewWindow)), 1);
				orient.getAdverbs().add(adverb);
				orient.setValue(orient.getValue() * adverb_dict.get(sunclauses.get(viewWindow)));
			}
			// 判断前一个词是否是否定词
			if (denial_dict.containsKey(sunclauses.get(viewWindow))) {
				Dictionary denial = new Dictionary(sunclauses.get(viewWindow),
						denial_dict.get(sunclauses.get(viewWindow)), 1);
				orient.getDenials().add(denial);
				orient.setValue(orient.getValue() * (-1));
			}

		}

		// 第2个视窗
		viewWindow = index - 2;
		// 无越界
		if (viewWindow > -1) {
			// 判断前一个词是否是情感词
			if (negative_dict.containsKey(sunclauses.get(viewWindow))
					|| negative_dict.containsKey(sunclauses.get(viewWindow))) {
				return orient;
			}
			// 判断前一个词是否是副词
			if (adverb_dict.containsKey(sunclauses.get(viewWindow))) {
				Dictionary adverb = new Dictionary(sunclauses.get(viewWindow),
						adverb_dict.get(sunclauses.get(viewWindow)), 2);
				orient.getAdverbs().add(0, adverb);
				orient.setValue(orient.getValue() * adverb_dict.get(sunclauses.get(viewWindow)));
			}
			// 判断前一个词是否是否定词
			if (denial_dict.containsKey(sunclauses.get(viewWindow))) {
				Dictionary denial = new Dictionary(sunclauses.get(viewWindow),
						denial_dict.get(sunclauses.get(viewWindow)), 2);
				orient.getDenials().add(0, denial);
				orient.setValue(orient.getValue() * (-1));
				// 判断是否是“不是很好”的结构（区别于“很不好”）
				if (orient.getAdverbs().size() > 0) {
					// 是，则引入调节阈值，0.3
					orient.setValue(orient.getValue() * 0.3);
				}

			}

		}

		// 第3个视窗
		viewWindow = index - 3;
		// 无越界
		if (viewWindow > -1) {
			// 判断前一个词是否是情感词
			if (negative_dict.containsKey(sunclauses.get(viewWindow))
					|| negative_dict.containsKey(sunclauses.get(viewWindow))) {
				return orient;
			}
			// 判断前一个词是否是副词
			if (adverb_dict.containsKey(sunclauses.get(viewWindow))) {
				Dictionary adverb = new Dictionary(sunclauses.get(viewWindow),
						adverb_dict.get(sunclauses.get(viewWindow)), 3);
				orient.getAdverbs().add(0, adverb);
				orient.setValue(orient.getValue() * adverb_dict.get(sunclauses.get(viewWindow)));
			}
			// 判断前一个词是否是否定词
			if (denial_dict.containsKey(sunclauses.get(viewWindow))) {
				Dictionary denial = new Dictionary(sunclauses.get(viewWindow),
						denial_dict.get(sunclauses.get(viewWindow)), 3);
				orient.getDenials().add(0, denial);
				orient.setValue(orient.getValue() * (-1));
				// 判断是否是“不是很好”的结构（区别于“很不好”）
				if (orient.getAdverbs().size() > 0 && orient.getDenials().size() == 0) {
					orient.setValue(orient.getValue() * 0.3);
				}
			}

		}
		return orient;
	}

	private Orientation isClausePattern1(String clause) {
		Pattern pattern = Pattern.compile(".*(要|选)的.+(送|给).*");
		Orientation clausePattern = null;
		Matcher matcher = pattern.matcher(clause);
		if (matcher.find()) {
			clausePattern = new Orientation("要的是…给的是…", 1);
		}
		return clausePattern;
	}

	private Orientation isClausePattern2(String clause) {
		Pattern pattern = Pattern.compile(".*(如果|要是|希望).+就[\u4e00-\u9fa5]+(好|完美)了");
		Orientation clausePattern = null;
		Matcher matcher = pattern.matcher(clause);
		if (matcher.find()) {
			clausePattern = new Orientation("如果…就好了", 1.0);
		}
		return clausePattern;
	}

	private List<String> divideSentenceIntoClauses(String sentence) {
		String[] split_clauses = sentence.trim().split("[，。%、！!？?,；～~.… ]+");
		List<String> punctuations = new ArrayList<>();
		List<String> clauses = new ArrayList<>();
		Pattern punctuationPattern = Pattern.compile("[，。%、！!？?,；～~.… ]+");
		Matcher punctuationMatcher = punctuationPattern.matcher(sentence.trim());
		while (punctuationMatcher.find()) {
			punctuations.add(punctuationMatcher.group());
		}
		for (int i = 0; i < split_clauses.length; i++) {
			clauses.add(split_clauses[i] + punctuations.get(i));
		}
		return clauses;
	}

}
