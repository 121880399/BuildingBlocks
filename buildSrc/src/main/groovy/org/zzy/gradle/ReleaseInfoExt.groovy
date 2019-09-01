
class ReleaseInfoExt{
    String versionName
    String versionCode
    String versionDes

    ReleaseInfoExt(){

    }

    @Override
    String toString() {
        return "versionName = ${versionName} | versionCode = ${versionCode} | versionDes = ${versionDes}"
    }
}