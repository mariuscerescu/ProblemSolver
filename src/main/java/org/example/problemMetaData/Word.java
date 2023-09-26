package org.example.problemMetaData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Word {
    @XmlValue
    private String content;
    @XmlAttribute(name = "LEMMA")
    private String lemma;
    @XmlAttribute(name = "MSD")
    private String msd;
    @XmlAttribute(name = "POS")
    private String pos;
    @XmlAttribute(name = "id")
    private String id;
    @XmlAttribute(name = "offset")
    private int offset;
    @XmlAttribute(name = "Case")
    private String caseAttribute;
    @XmlAttribute(name = "Definiteness")
    private String definiteness;
    @XmlAttribute(name = "Gender")
    private String gender;
    @XmlAttribute(name = "Number")
    private String number;
    @XmlAttribute(name = "Type")
    private String type;
    @XmlAttribute(name = "Mood")
    private String mood;
    @XmlAttribute(name = "Person")
    private String person;
    @XmlAttribute(name = "Tense")
    private String tense;
    @XmlAttribute(name = "EXTRA")
    private String extra;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getMsd() {
        return msd;
    }

    public void setMsd(String msd) {
        this.msd = msd;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getCaseAttribute() {
        return caseAttribute;
    }

    public void setCaseAttribute(String caseAttribute) {
        this.caseAttribute = caseAttribute;
    }

    public String getDefiniteness() {
        return definiteness;
    }

    public void setDefiniteness(String definiteness) {
        this.definiteness = definiteness;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTense() {
        return tense;
    }

    public void setTense(String tense) {
        this.tense = tense;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
