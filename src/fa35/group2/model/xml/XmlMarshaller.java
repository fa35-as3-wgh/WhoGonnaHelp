package fa35.group2.model.xml;

import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlMarshaller {

    private static class SchemaErrorHandler implements ErrorHandler {
        @Override
        public void warning(SAXParseException exception) throws SAXException {
            System.out.println("Warning: " + exception.getMessage() + " getColumnNumber is " +
                    exception.getColumnNumber() + " getLineNumber " + exception.getLineNumber() + " getPublicId " +
                    exception.getPublicId() + " getSystemId " + exception.getSystemId());
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            throw new SAXException("Error: " + exception.getMessage() + " getColumnNumber is " +
                    exception.getColumnNumber() + " getLineNumber " + exception.getLineNumber() + " getPublicId " +
                    exception.getPublicId() + " getSystemId " + exception.getSystemId());
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            throw new SAXException("Fatal Error: " + exception.getMessage() + " getColumnNumber is " +
                    exception.getColumnNumber() + " getLineNumber " + exception.getLineNumber() + " getPublicId " +
                    exception.getPublicId() + " getSystemId " + exception.getSystemId());
        }
    }

    private static DocumentBuilder documentBuilder;
    private static Transformer transformer;

    static {
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            Schema schema = factory.newSchema(XmlMarshaller.class.getResource("/fa35/group2/model/xml/schema.xsd"));

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setSchema(schema);
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            SchemaErrorHandler errorHandler = new SchemaErrorHandler();
            documentBuilder.setErrorHandler(errorHandler);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        } catch (ParserConfigurationException | TransformerConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Document document;
    private Map<Integer, PaymentEntity> paymentEntityMap;
    private Map<Integer, SkillEntity> skillEntityMap;
    private Map<Integer, FriendEntity> friendEntityMap;

    public XmlMarshaller(Map<Integer, PaymentEntity> paymentEntityMap,
                         Map<Integer, SkillEntity> skillEntityMap,
                         Map<Integer, FriendEntity> friendEntityMap) {
        this.paymentEntityMap = paymentEntityMap;
        this.skillEntityMap = skillEntityMap;
        this.friendEntityMap = friendEntityMap;
    }

    public void marshal() {
        this.document = documentBuilder.newDocument();

        Element root = this.document.createElement("persistence");
        Attr attr = this.document.createAttribute("name");
        attr.setValue("who_gonna_help");
        root.setAttributeNode(attr);

        root.appendChild(marshalPayments());
        root.appendChild(marshalSkills());
        root.appendChild(marshalFriend());

        this.document.appendChild(root);
        this.document.setXmlStandalone(true);
    }

    public Element marshalPayments() {
        Element payments = this.document.createElement("payments");

        if (this.paymentEntityMap != null) {
            this.paymentEntityMap.forEach((integer, entity) ->
            {
                Element payment = this.document.createElement("payment");

                Attr attr = this.document.createAttribute("payment_id");
                attr.setValue(Integer.toString(entity.getId()));
                payment.setAttributeNode(attr);

                attr = this.document.createAttribute("name");
                attr.setValue(escapeXml(entity.getName()));
                payment.setAttributeNode(attr);

                payments.appendChild(payment);
            });
        }

        return payments;
    }

    public Element marshalSkills() {
        Element skills = this.document.createElement("skills");

        if (this.skillEntityMap != null) {
            this.skillEntityMap.forEach((integer, entity) ->
            {
                Element skill = this.document.createElement("skill");

                Attr attr = this.document.createAttribute("skill_id");
                attr.setValue(Integer.toString(entity.getId()));
                skill.setAttributeNode(attr);

                attr = this.document.createAttribute("name");
                attr.setValue(escapeXml(entity.getName()));
                skill.setAttributeNode(attr);

                skills.appendChild(skill);
            });
        }

        return skills;
    }

    public Element marshalFriend() {
        Element friends = this.document.createElement("friends");

        if (friendEntityMap != null) {
            friendEntityMap.forEach((integer, entity) ->
            {
                Element friend = this.document.createElement("friend");

                Attr attr = this.document.createAttribute("friend_id");
                attr.setValue(Integer.toString(entity.getId()));
                friend.setAttributeNode(attr);

                attr = this.document.createAttribute("name");
                attr.setValue(escapeXml(entity.getName()));
                friend.setAttributeNode(attr);

                attr = this.document.createAttribute("contact");
                attr.setValue(escapeXml(entity.getContact()));
                friend.setAttributeNode(attr);

                attr = this.document.createAttribute("note");
                attr.setValue(escapeXml(entity.getNote()));
                friend.setAttributeNode(attr);

                friend.appendChild(marshalFriendPayments(entity.getPayments()));
                friend.appendChild(marshalFriendSkills(entity.getSkills()));

                friends.appendChild(friend);
            });
        }

        return friends;
    }

    private Element marshalFriendPayments(List<PaymentEntity> paymentEntities) {
        Element payments = this.document.createElement("payments");

        if (paymentEntities != null) {
            paymentEntities.forEach(entity ->
            {
                Element skill = this.document.createElement("payment");

                Attr attr = this.document.createAttribute("payment_id");
                attr.setValue(Integer.toString(entity.getId()));
                skill.setAttributeNode(attr);

                payments.appendChild(skill);
            });
        }

        return payments;
    }

    private Element marshalFriendSkills(List<SkillEntity> skillEntities) {
        Element skills = this.document.createElement("skills");

        if (skillEntities != null) {
            skillEntities.forEach(entity ->
            {
                Element skill = this.document.createElement("skill");

                Attr attr = this.document.createAttribute("skill_id");
                attr.setValue(Integer.toString(entity.getId()));
                skill.setAttributeNode(attr);

                skills.appendChild(skill);
            });
        }

        return skills;
    }

    public void save(File file) throws TransformerException, NullPointerException {
        if (this.document != null && file != null) {
            DOMSource source = new DOMSource(this.document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } else throw new NullPointerException("Document or file is null");
    }

    public String toXmlString() throws TransformerException, NullPointerException {
        if (this.document != null) {
            DOMSource source = new DOMSource(this.document);
            OutputStream output = new OutputStream() {
                StringBuilder stringBuilder = new StringBuilder();

                @Override
                public void write(int byteToWrite) throws IOException {
                    stringBuilder.append((char) byteToWrite);
                }

                @Override
                public String toString() {
                    return stringBuilder.toString();
                }
            };
            transformer.transform(source, new StreamResult(output));

            return output.toString();
        } else throw new NullPointerException("Document or file is null");
    }

    public void unmarshal() {

        if (this.document != null) {
            Element element = this.document.getDocumentElement();
            element.normalize();
            if ("persistence".equals(element.getNodeName()) && "who_gonna_help".equals(element.getAttribute("name"))) {

                NodeList children = element.getChildNodes();

                for (int index = 0; index < children.getLength(); index++) {
                    Node child = children.item(index);
                    switch (child.getNodeName()) {
                        case "payments":
                            unmarshalPayments(child);
                            break;
                        case "skills":
                            unmarshalSkills(child);
                            break;
                        case "friends":
                            unmarshalFriends(child);
                            break;
                    }
                }
            }
        }
    }

    private void unmarshalPayments(Node paymentNode) {
        NodeList paymentNodes = paymentNode.getChildNodes();
        if (paymentNodes != null) {
            for (int index = 0; index < paymentNodes.getLength(); index++) {
                Node payment = paymentNodes.item(index);

                if ("payment".equals(payment.getNodeName())) {
                    NamedNodeMap attributes = payment.getAttributes();
                    String idString = attributes.getNamedItem("payment_id").getTextContent();
                    if (idString.matches("\\d+")) {
                        int id = Integer.parseInt(idString);
                        PaymentEntity paymentEntity = new PaymentEntity();
                        paymentEntity.setId(id);
                        paymentEntity.setName(unescapeXml(attributes.getNamedItem("name").getTextContent()));

                        this.paymentEntityMap.put(id, paymentEntity);
                    }
                }
            }
        }
    }

    private void unmarshalSkills(Node skillNode) {
        NodeList skillNodes = skillNode.getChildNodes();
        if (skillNodes != null) {
            for (int index = 0; index < skillNodes.getLength(); index++) {
                Node skill = skillNodes.item(index);

                if ("skill".equals(skill.getNodeName())) {
                    NamedNodeMap attributes = skill.getAttributes();
                    String idString = attributes.getNamedItem("skill_id").getTextContent();
                    if (idString.matches("\\d+")) {
                        int id = Integer.parseInt(idString);
                        SkillEntity skillEntity = new SkillEntity();
                        skillEntity.setId(id);
                        skillEntity.setName(unescapeXml(attributes.getNamedItem("name").getTextContent()));

                        this.skillEntityMap.put(id, skillEntity);
                    }
                }
            }
        }
    }

    private void unmarshalFriends(Node friendNode) {
        NodeList friendNodes = friendNode.getChildNodes();
        if (friendNodes != null) {
            for (int index = 0; index < friendNodes.getLength(); index++) {
                Node friend = friendNodes.item(index);

                if ("friend".equals(friend.getNodeName())) {
                    NamedNodeMap attributes = friend.getAttributes();
                    String idString = attributes.getNamedItem("friend_id").getTextContent();
                    if (idString.matches("\\d+")) {
                        int id = Integer.parseInt(idString);
                        FriendEntity friendEntity = new FriendEntity();
                        friendEntity.setId(id);
                        friendEntity.setName(unescapeXml(attributes.getNamedItem("name").getTextContent()));
                        friendEntity.setContact(unescapeXml(attributes.getNamedItem("contact").getTextContent()));
                        friendEntity.setNote(unescapeXml(attributes.getNamedItem("note").getTextContent()));

                        NodeList friendChildren = friend.getChildNodes();
                        for (int childIndex = 0; childIndex < friendChildren.getLength(); childIndex++) {
                            Node friendChild = friendChildren.item(childIndex);
                            switch (friendChild.getNodeName()) {
                                case "payments":
                                    friendEntity.getPayments().addAll(unmarshalFriendPayments(friendChild.getChildNodes()));
                                    break;
                                case "skills":
                                    friendEntity.getSkills().addAll(unmarshalFriendSkills(friendChild.getChildNodes()));
                                    break;
                            }
                        }

                        this.friendEntityMap.put(id, friendEntity);
                    }
                }
            }
        }
    }

    private List<PaymentEntity> unmarshalFriendPayments(NodeList paymentNodes) {

        List<PaymentEntity> paymentEntities = new ArrayList<PaymentEntity>();

        if (paymentNodes != null) {
            for (int index = 0; index < paymentNodes.getLength(); index++) {
                Node skill = paymentNodes.item(index);

                if ("payment".equals(skill.getNodeName())) {
                    NamedNodeMap attributes = skill.getAttributes();
                    String idString = attributes.getNamedItem("payment_id").getTextContent();
                    if (idString.matches("\\d+")) {
                        int id = Integer.parseInt(idString);

                        PaymentEntity paymentEntity = this.paymentEntityMap.get(id);
                        if (paymentEntity != null) {
                            paymentEntities.add(paymentEntity);
                        }
                    }
                }
            }
        }

        return paymentEntities;
    }

    private List<SkillEntity> unmarshalFriendSkills(NodeList skillNodes) {

        List<SkillEntity> skillEntities = new ArrayList<SkillEntity>();

        if (skillNodes != null) {
            for (int index = 0; index < skillNodes.getLength(); index++) {
                Node skill = skillNodes.item(index);

                if ("skill".equals(skill.getNodeName())) {
                    NamedNodeMap attributes = skill.getAttributes();
                    String idString = attributes.getNamedItem("skill_id").getTextContent();
                    if (idString.matches("\\d+")) {
                        int id = Integer.parseInt(idString);

                        SkillEntity skillEntity = this.skillEntityMap.get(id);
                        if (skillEntity != null) {
                            skillEntities.add(skillEntity);
                        }
                    }
                }
            }
        }

        return skillEntities;
    }

    public void load(File file) throws IOException, SAXException, NullPointerException {
        if (file != null) {
            this.document = documentBuilder.parse(file);
            this.document.normalize();
        } else throw new NullPointerException("File is null");
    }

    private String escapeXml(String text) {
        if (text == null)
            return null;

        StringBuffer result = new StringBuffer();
        text.chars().mapToObj(value -> (char) value).forEach(character -> {
            switch (character) {
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '\'':
                    result.append("&apos;");
                    break;
                case '\"':
                    result.append("&quot;");
                    break;
                case '\t':
                    result.append("&#x9;");
                    break;
                case '\n':
                    result.append("&#xA;");
                    break;
                case '\r':
                    result.append("&#xD;");
                    break;
                default:
                    if (character > 0x7e) {
                        result.append("&#" + ((int) character) + ";");
                    } else
                        result.append(character);
            }
        });
        return result.toString();
    }

    private String unescapeXml(String text) {
        if (text == null)
            return null;

        Pattern xmlEntityRegex = Pattern.compile("&(#?)([^;]+);");
        StringBuffer unescapedOutput = new StringBuffer(text.length());

        Matcher m = xmlEntityRegex.matcher(text);
        String entity;
        String hashmark;
        String ent;
        int code;
        while (m.find()) {
            ent = m.group(2);
            hashmark = m.group(1);
            if (hashmark != null && hashmark.length() > 0) {
                code = Integer.parseInt(ent);
                entity = Character.toString((char) code);
            } else {
                switch (ent) {
                    case "&lt;":
                        entity = "<";
                        break;
                    case "&gt;":
                        entity = ">";
                        break;
                    case "&amp;":
                        entity = "&";
                        break;
                    case "&apos;":
                        entity = "\'";
                        break;
                    case "&quot;":
                        entity = "\"";
                        break;
                    default:
                        //not a known entity - ignore it
                        entity = "&" + ent + ';';
                        break;
                }
            }
            m.appendReplacement(unescapedOutput, entity);
        }
        m.appendTail(unescapedOutput);

        return unescapedOutput.toString();
    }
}
