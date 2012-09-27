// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: player.proto

package proto;

public final class ProtoPlayer {
  private ProtoPlayer() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface CommandOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // required .proto.Command.Type type = 1;
    boolean hasType();
    proto.ProtoPlayer.Command.Type getType();
    
    // optional .proto.Command.Information info = 2;
    boolean hasInfo();
    proto.ProtoPlayer.Command.Information getInfo();
    proto.ProtoPlayer.Command.InformationOrBuilder getInfoOrBuilder();
  }
  public static final class Command extends
      com.google.protobuf.GeneratedMessage
      implements CommandOrBuilder {
    // Use Command.newBuilder() to construct.
    private Command(Builder builder) {
      super(builder);
    }
    private Command(boolean noInit) {}
    
    private static final Command defaultInstance;
    public static Command getDefaultInstance() {
      return defaultInstance;
    }
    
    public Command getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return proto.ProtoPlayer.internal_static_proto_Command_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return proto.ProtoPlayer.internal_static_proto_Command_fieldAccessorTable;
    }
    
    public enum Type
        implements com.google.protobuf.ProtocolMessageEnum {
      SET_POSITION(0, 1),
      PREVIOUS_CHAPTER(1, 2),
      NEXT_CHAPTER(2, 3),
      REWIND(3, 4),
      FAST_FORWARD(4, 5),
      STOP(5, 6),
      PLAY(6, 7),
      PAUSE(7, 8),
      MUTE(8, 9),
      SET_VOLUME(9, 10),
      TOGGLE_FULL_SCREEN(10, 11),
      START_MOVIE(11, 12),
      ;
      
      public static final int SET_POSITION_VALUE = 1;
      public static final int PREVIOUS_CHAPTER_VALUE = 2;
      public static final int NEXT_CHAPTER_VALUE = 3;
      public static final int REWIND_VALUE = 4;
      public static final int FAST_FORWARD_VALUE = 5;
      public static final int STOP_VALUE = 6;
      public static final int PLAY_VALUE = 7;
      public static final int PAUSE_VALUE = 8;
      public static final int MUTE_VALUE = 9;
      public static final int SET_VOLUME_VALUE = 10;
      public static final int TOGGLE_FULL_SCREEN_VALUE = 11;
      public static final int START_MOVIE_VALUE = 12;
      
      
      public final int getNumber() { return value; }
      
      public static Type valueOf(int value) {
        switch (value) {
          case 1: return SET_POSITION;
          case 2: return PREVIOUS_CHAPTER;
          case 3: return NEXT_CHAPTER;
          case 4: return REWIND;
          case 5: return FAST_FORWARD;
          case 6: return STOP;
          case 7: return PLAY;
          case 8: return PAUSE;
          case 9: return MUTE;
          case 10: return SET_VOLUME;
          case 11: return TOGGLE_FULL_SCREEN;
          case 12: return START_MOVIE;
          default: return null;
        }
      }
      
      public static com.google.protobuf.Internal.EnumLiteMap<Type>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static com.google.protobuf.Internal.EnumLiteMap<Type>
          internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<Type>() {
              public Type findValueByNumber(int number) {
                return Type.valueOf(number);
              }
            };
      
      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        return getDescriptor().getValues().get(index);
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return proto.ProtoPlayer.Command.getDescriptor().getEnumTypes().get(0);
      }
      
      private static final Type[] VALUES = {
        SET_POSITION, PREVIOUS_CHAPTER, NEXT_CHAPTER, REWIND, FAST_FORWARD, STOP, PLAY, PAUSE, MUTE, SET_VOLUME, TOGGLE_FULL_SCREEN, START_MOVIE, 
      };
      
      public static Type valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        return VALUES[desc.getIndex()];
      }
      
      private final int index;
      private final int value;
      
      private Type(int index, int value) {
        this.index = index;
        this.value = value;
      }
      
      // @@protoc_insertion_point(enum_scope:proto.Command.Type)
    }
    
    public interface InformationOrBuilder
        extends com.google.protobuf.MessageOrBuilder {
      
      // required string value = 1;
      boolean hasValue();
      String getValue();
    }
    public static final class Information extends
        com.google.protobuf.GeneratedMessage
        implements InformationOrBuilder {
      // Use Information.newBuilder() to construct.
      private Information(Builder builder) {
        super(builder);
      }
      private Information(boolean noInit) {}
      
      private static final Information defaultInstance;
      public static Information getDefaultInstance() {
        return defaultInstance;
      }
      
      public Information getDefaultInstanceForType() {
        return defaultInstance;
      }
      
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return proto.ProtoPlayer.internal_static_proto_Command_Information_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return proto.ProtoPlayer.internal_static_proto_Command_Information_fieldAccessorTable;
      }
      
      private int bitField0_;
      // required string value = 1;
      public static final int VALUE_FIELD_NUMBER = 1;
      private java.lang.Object value_;
      public boolean hasValue() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public String getValue() {
        java.lang.Object ref = value_;
        if (ref instanceof String) {
          return (String) ref;
        } else {
          com.google.protobuf.ByteString bs = 
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          if (com.google.protobuf.Internal.isValidUtf8(bs)) {
            value_ = s;
          }
          return s;
        }
      }
      private com.google.protobuf.ByteString getValueBytes() {
        java.lang.Object ref = value_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8((String) ref);
          value_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      
      private void initFields() {
        value_ = "";
      }
      private byte memoizedIsInitialized = -1;
      public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized != -1) return isInitialized == 1;
        
        if (!hasValue()) {
          memoizedIsInitialized = 0;
          return false;
        }
        memoizedIsInitialized = 1;
        return true;
      }
      
      public void writeTo(com.google.protobuf.CodedOutputStream output)
                          throws java.io.IOException {
        getSerializedSize();
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          output.writeBytes(1, getValueBytes());
        }
        getUnknownFields().writeTo(output);
      }
      
      private int memoizedSerializedSize = -1;
      public int getSerializedSize() {
        int size = memoizedSerializedSize;
        if (size != -1) return size;
      
        size = 0;
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          size += com.google.protobuf.CodedOutputStream
            .computeBytesSize(1, getValueBytes());
        }
        size += getUnknownFields().getSerializedSize();
        memoizedSerializedSize = size;
        return size;
      }
      
      private static final long serialVersionUID = 0L;
      @java.lang.Override
      protected java.lang.Object writeReplace()
          throws java.io.ObjectStreamException {
        return super.writeReplace();
      }
      
      public static proto.ProtoPlayer.Command.Information parseFrom(
          com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return newBuilder().mergeFrom(data).buildParsed();
      }
      public static proto.ProtoPlayer.Command.Information parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return newBuilder().mergeFrom(data, extensionRegistry)
                 .buildParsed();
      }
      public static proto.ProtoPlayer.Command.Information parseFrom(byte[] data)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return newBuilder().mergeFrom(data).buildParsed();
      }
      public static proto.ProtoPlayer.Command.Information parseFrom(
          byte[] data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return newBuilder().mergeFrom(data, extensionRegistry)
                 .buildParsed();
      }
      public static proto.ProtoPlayer.Command.Information parseFrom(java.io.InputStream input)
          throws java.io.IOException {
        return newBuilder().mergeFrom(input).buildParsed();
      }
      public static proto.ProtoPlayer.Command.Information parseFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        return newBuilder().mergeFrom(input, extensionRegistry)
                 .buildParsed();
      }
      public static proto.ProtoPlayer.Command.Information parseDelimitedFrom(java.io.InputStream input)
          throws java.io.IOException {
        Builder builder = newBuilder();
        if (builder.mergeDelimitedFrom(input)) {
          return builder.buildParsed();
        } else {
          return null;
        }
      }
      public static proto.ProtoPlayer.Command.Information parseDelimitedFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Builder builder = newBuilder();
        if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
          return builder.buildParsed();
        } else {
          return null;
        }
      }
      public static proto.ProtoPlayer.Command.Information parseFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
        return newBuilder().mergeFrom(input).buildParsed();
      }
      public static proto.ProtoPlayer.Command.Information parseFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        return newBuilder().mergeFrom(input, extensionRegistry)
                 .buildParsed();
      }
      
      public static Builder newBuilder() { return Builder.create(); }
      public Builder newBuilderForType() { return newBuilder(); }
      public static Builder newBuilder(proto.ProtoPlayer.Command.Information prototype) {
        return newBuilder().mergeFrom(prototype);
      }
      public Builder toBuilder() { return newBuilder(this); }
      
      @java.lang.Override
      protected Builder newBuilderForType(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
      }
      public static final class Builder extends
          com.google.protobuf.GeneratedMessage.Builder<Builder>
         implements proto.ProtoPlayer.Command.InformationOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
          return proto.ProtoPlayer.internal_static_proto_Command_Information_descriptor;
        }
        
        protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internalGetFieldAccessorTable() {
          return proto.ProtoPlayer.internal_static_proto_Command_Information_fieldAccessorTable;
        }
        
        // Construct using proto.ProtoPlayer.Command.Information.newBuilder()
        private Builder() {
          maybeForceBuilderInitialization();
        }
        
        private Builder(BuilderParent parent) {
          super(parent);
          maybeForceBuilderInitialization();
        }
        private void maybeForceBuilderInitialization() {
          if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          }
        }
        private static Builder create() {
          return new Builder();
        }
        
        public Builder clear() {
          super.clear();
          value_ = "";
          bitField0_ = (bitField0_ & ~0x00000001);
          return this;
        }
        
        public Builder clone() {
          return create().mergeFrom(buildPartial());
        }
        
        public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
          return proto.ProtoPlayer.Command.Information.getDescriptor();
        }
        
        public proto.ProtoPlayer.Command.Information getDefaultInstanceForType() {
          return proto.ProtoPlayer.Command.Information.getDefaultInstance();
        }
        
        public proto.ProtoPlayer.Command.Information build() {
          proto.ProtoPlayer.Command.Information result = buildPartial();
          if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
          }
          return result;
        }
        
        private proto.ProtoPlayer.Command.Information buildParsed()
            throws com.google.protobuf.InvalidProtocolBufferException {
          proto.ProtoPlayer.Command.Information result = buildPartial();
          if (!result.isInitialized()) {
            throw newUninitializedMessageException(
              result).asInvalidProtocolBufferException();
          }
          return result;
        }
        
        public proto.ProtoPlayer.Command.Information buildPartial() {
          proto.ProtoPlayer.Command.Information result = new proto.ProtoPlayer.Command.Information(this);
          int from_bitField0_ = bitField0_;
          int to_bitField0_ = 0;
          if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
            to_bitField0_ |= 0x00000001;
          }
          result.value_ = value_;
          result.bitField0_ = to_bitField0_;
          onBuilt();
          return result;
        }
        
        public Builder mergeFrom(com.google.protobuf.Message other) {
          if (other instanceof proto.ProtoPlayer.Command.Information) {
            return mergeFrom((proto.ProtoPlayer.Command.Information)other);
          } else {
            super.mergeFrom(other);
            return this;
          }
        }
        
        public Builder mergeFrom(proto.ProtoPlayer.Command.Information other) {
          if (other == proto.ProtoPlayer.Command.Information.getDefaultInstance()) return this;
          if (other.hasValue()) {
            setValue(other.getValue());
          }
          this.mergeUnknownFields(other.getUnknownFields());
          return this;
        }
        
        public final boolean isInitialized() {
          if (!hasValue()) {
            
            return false;
          }
          return true;
        }
        
        public Builder mergeFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
          com.google.protobuf.UnknownFieldSet.Builder unknownFields =
            com.google.protobuf.UnknownFieldSet.newBuilder(
              this.getUnknownFields());
          while (true) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              default: {
                if (!parseUnknownField(input, unknownFields,
                                       extensionRegistry, tag)) {
                  this.setUnknownFields(unknownFields.build());
                  onChanged();
                  return this;
                }
                break;
              }
              case 10: {
                bitField0_ |= 0x00000001;
                value_ = input.readBytes();
                break;
              }
            }
          }
        }
        
        private int bitField0_;
        
        // required string value = 1;
        private java.lang.Object value_ = "";
        public boolean hasValue() {
          return ((bitField0_ & 0x00000001) == 0x00000001);
        }
        public String getValue() {
          java.lang.Object ref = value_;
          if (!(ref instanceof String)) {
            String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
            value_ = s;
            return s;
          } else {
            return (String) ref;
          }
        }
        public Builder setValue(String value) {
          if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
          value_ = value;
          onChanged();
          return this;
        }
        public Builder clearValue() {
          bitField0_ = (bitField0_ & ~0x00000001);
          value_ = getDefaultInstance().getValue();
          onChanged();
          return this;
        }
        void setValue(com.google.protobuf.ByteString value) {
          bitField0_ |= 0x00000001;
          value_ = value;
          onChanged();
        }
        
        // @@protoc_insertion_point(builder_scope:proto.Command.Information)
      }
      
      static {
        defaultInstance = new Information(true);
        defaultInstance.initFields();
      }
      
      // @@protoc_insertion_point(class_scope:proto.Command.Information)
    }
    
    private int bitField0_;
    // required .proto.Command.Type type = 1;
    public static final int TYPE_FIELD_NUMBER = 1;
    private proto.ProtoPlayer.Command.Type type_;
    public boolean hasType() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public proto.ProtoPlayer.Command.Type getType() {
      return type_;
    }
    
    // optional .proto.Command.Information info = 2;
    public static final int INFO_FIELD_NUMBER = 2;
    private proto.ProtoPlayer.Command.Information info_;
    public boolean hasInfo() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    public proto.ProtoPlayer.Command.Information getInfo() {
      return info_;
    }
    public proto.ProtoPlayer.Command.InformationOrBuilder getInfoOrBuilder() {
      return info_;
    }
    
    private void initFields() {
      type_ = proto.ProtoPlayer.Command.Type.SET_POSITION;
      info_ = proto.ProtoPlayer.Command.Information.getDefaultInstance();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      if (!hasType()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (hasInfo()) {
        if (!getInfo().isInitialized()) {
          memoizedIsInitialized = 0;
          return false;
        }
      }
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeEnum(1, type_.getNumber());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeMessage(2, info_);
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(1, type_.getNumber());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, info_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static proto.ProtoPlayer.Command parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static proto.ProtoPlayer.Command parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static proto.ProtoPlayer.Command parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static proto.ProtoPlayer.Command parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static proto.ProtoPlayer.Command parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static proto.ProtoPlayer.Command parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static proto.ProtoPlayer.Command parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static proto.ProtoPlayer.Command parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static proto.ProtoPlayer.Command parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static proto.ProtoPlayer.Command parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(proto.ProtoPlayer.Command prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements proto.ProtoPlayer.CommandOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return proto.ProtoPlayer.internal_static_proto_Command_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return proto.ProtoPlayer.internal_static_proto_Command_fieldAccessorTable;
      }
      
      // Construct using proto.ProtoPlayer.Command.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          getInfoFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        type_ = proto.ProtoPlayer.Command.Type.SET_POSITION;
        bitField0_ = (bitField0_ & ~0x00000001);
        if (infoBuilder_ == null) {
          info_ = proto.ProtoPlayer.Command.Information.getDefaultInstance();
        } else {
          infoBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return proto.ProtoPlayer.Command.getDescriptor();
      }
      
      public proto.ProtoPlayer.Command getDefaultInstanceForType() {
        return proto.ProtoPlayer.Command.getDefaultInstance();
      }
      
      public proto.ProtoPlayer.Command build() {
        proto.ProtoPlayer.Command result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private proto.ProtoPlayer.Command buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        proto.ProtoPlayer.Command result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public proto.ProtoPlayer.Command buildPartial() {
        proto.ProtoPlayer.Command result = new proto.ProtoPlayer.Command(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.type_ = type_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        if (infoBuilder_ == null) {
          result.info_ = info_;
        } else {
          result.info_ = infoBuilder_.build();
        }
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof proto.ProtoPlayer.Command) {
          return mergeFrom((proto.ProtoPlayer.Command)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(proto.ProtoPlayer.Command other) {
        if (other == proto.ProtoPlayer.Command.getDefaultInstance()) return this;
        if (other.hasType()) {
          setType(other.getType());
        }
        if (other.hasInfo()) {
          mergeInfo(other.getInfo());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        if (!hasType()) {
          
          return false;
        }
        if (hasInfo()) {
          if (!getInfo().isInitialized()) {
            
            return false;
          }
        }
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 8: {
              int rawValue = input.readEnum();
              proto.ProtoPlayer.Command.Type value = proto.ProtoPlayer.Command.Type.valueOf(rawValue);
              if (value == null) {
                unknownFields.mergeVarintField(1, rawValue);
              } else {
                bitField0_ |= 0x00000001;
                type_ = value;
              }
              break;
            }
            case 18: {
              proto.ProtoPlayer.Command.Information.Builder subBuilder = proto.ProtoPlayer.Command.Information.newBuilder();
              if (hasInfo()) {
                subBuilder.mergeFrom(getInfo());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setInfo(subBuilder.buildPartial());
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // required .proto.Command.Type type = 1;
      private proto.ProtoPlayer.Command.Type type_ = proto.ProtoPlayer.Command.Type.SET_POSITION;
      public boolean hasType() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public proto.ProtoPlayer.Command.Type getType() {
        return type_;
      }
      public Builder setType(proto.ProtoPlayer.Command.Type value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000001;
        type_ = value;
        onChanged();
        return this;
      }
      public Builder clearType() {
        bitField0_ = (bitField0_ & ~0x00000001);
        type_ = proto.ProtoPlayer.Command.Type.SET_POSITION;
        onChanged();
        return this;
      }
      
      // optional .proto.Command.Information info = 2;
      private proto.ProtoPlayer.Command.Information info_ = proto.ProtoPlayer.Command.Information.getDefaultInstance();
      private com.google.protobuf.SingleFieldBuilder<
          proto.ProtoPlayer.Command.Information, proto.ProtoPlayer.Command.Information.Builder, proto.ProtoPlayer.Command.InformationOrBuilder> infoBuilder_;
      public boolean hasInfo() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      public proto.ProtoPlayer.Command.Information getInfo() {
        if (infoBuilder_ == null) {
          return info_;
        } else {
          return infoBuilder_.getMessage();
        }
      }
      public Builder setInfo(proto.ProtoPlayer.Command.Information value) {
        if (infoBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          info_ = value;
          onChanged();
        } else {
          infoBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      public Builder setInfo(
          proto.ProtoPlayer.Command.Information.Builder builderForValue) {
        if (infoBuilder_ == null) {
          info_ = builderForValue.build();
          onChanged();
        } else {
          infoBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      public Builder mergeInfo(proto.ProtoPlayer.Command.Information value) {
        if (infoBuilder_ == null) {
          if (((bitField0_ & 0x00000002) == 0x00000002) &&
              info_ != proto.ProtoPlayer.Command.Information.getDefaultInstance()) {
            info_ =
              proto.ProtoPlayer.Command.Information.newBuilder(info_).mergeFrom(value).buildPartial();
          } else {
            info_ = value;
          }
          onChanged();
        } else {
          infoBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      public Builder clearInfo() {
        if (infoBuilder_ == null) {
          info_ = proto.ProtoPlayer.Command.Information.getDefaultInstance();
          onChanged();
        } else {
          infoBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }
      public proto.ProtoPlayer.Command.Information.Builder getInfoBuilder() {
        bitField0_ |= 0x00000002;
        onChanged();
        return getInfoFieldBuilder().getBuilder();
      }
      public proto.ProtoPlayer.Command.InformationOrBuilder getInfoOrBuilder() {
        if (infoBuilder_ != null) {
          return infoBuilder_.getMessageOrBuilder();
        } else {
          return info_;
        }
      }
      private com.google.protobuf.SingleFieldBuilder<
          proto.ProtoPlayer.Command.Information, proto.ProtoPlayer.Command.Information.Builder, proto.ProtoPlayer.Command.InformationOrBuilder> 
          getInfoFieldBuilder() {
        if (infoBuilder_ == null) {
          infoBuilder_ = new com.google.protobuf.SingleFieldBuilder<
              proto.ProtoPlayer.Command.Information, proto.ProtoPlayer.Command.Information.Builder, proto.ProtoPlayer.Command.InformationOrBuilder>(
                  info_,
                  getParentForChildren(),
                  isClean());
          info_ = null;
        }
        return infoBuilder_;
      }
      
      // @@protoc_insertion_point(builder_scope:proto.Command)
    }
    
    static {
      defaultInstance = new Command(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:proto.Command)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_Command_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_proto_Command_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_Command_Information_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_proto_Command_Information_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014player.proto\022\005proto\"\267\002\n\007Command\022!\n\004typ" +
      "e\030\001 \002(\0162\023.proto.Command.Type\022(\n\004info\030\002 \001" +
      "(\0132\032.proto.Command.Information\032\034\n\013Inform" +
      "ation\022\r\n\005value\030\001 \002(\t\"\300\001\n\004Type\022\020\n\014SET_POS" +
      "ITION\020\001\022\024\n\020PREVIOUS_CHAPTER\020\002\022\020\n\014NEXT_CH" +
      "APTER\020\003\022\n\n\006REWIND\020\004\022\020\n\014FAST_FORWARD\020\005\022\010\n" +
      "\004STOP\020\006\022\010\n\004PLAY\020\007\022\t\n\005PAUSE\020\010\022\010\n\004MUTE\020\t\022\016" +
      "\n\nSET_VOLUME\020\n\022\026\n\022TOGGLE_FULL_SCREEN\020\013\022\017" +
      "\n\013START_MOVIE\020\014B\rB\013ProtoPlayer"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_proto_Command_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_proto_Command_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_proto_Command_descriptor,
              new java.lang.String[] { "Type", "Info", },
              proto.ProtoPlayer.Command.class,
              proto.ProtoPlayer.Command.Builder.class);
          internal_static_proto_Command_Information_descriptor =
            internal_static_proto_Command_descriptor.getNestedTypes().get(0);
          internal_static_proto_Command_Information_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_proto_Command_Information_descriptor,
              new java.lang.String[] { "Value", },
              proto.ProtoPlayer.Command.Information.class,
              proto.ProtoPlayer.Command.Information.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}
